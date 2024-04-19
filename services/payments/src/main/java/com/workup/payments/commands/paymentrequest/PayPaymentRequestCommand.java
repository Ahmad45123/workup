package com.workup.payments.commands.paymentrequest;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.models.PaymentRequest;
import com.workup.payments.models.PaymentTransaction;
import com.workup.payments.models.Wallet;
import com.workup.payments.models.WalletTransaction;
import com.workup.shared.commands.payments.paymentrequest.requests.PayPaymentRequestRequest;
import com.workup.shared.commands.payments.paymentrequest.responses.PayPaymentRequestResponse;
import com.workup.shared.enums.payments.PaymentRequestStatus;
import com.workup.shared.enums.payments.PaymentTransactionStatus;
import com.workup.shared.enums.payments.WalletTransactionType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class PayPaymentRequestCommand extends PaymentCommand<PayPaymentRequestRequest, PayPaymentRequestResponse> {

    @Override
    @Transactional
    public PayPaymentRequestResponse Run(PayPaymentRequestRequest request) {
        Optional<PaymentRequest> paymentRequest = getPaymentRequestRepository().findById(request.getPaymentRequestId());
        if (paymentRequest.isEmpty()) {
            return PayPaymentRequestResponse.builder()
                    .withSuccess(false)
                    .withTransactionId(null)
                    .withTransactionStatus(null)
                    .build();
        }
        PaymentTransaction paymentTransaction = PaymentTransaction.builder()
                .withPaymentRequestId(paymentRequest.get().getId())
                .withAmount(paymentRequest.get().getAmount())
                .withStatus(PaymentTransactionStatus.SUCCESS)
                .build();
        try {
            PaymentTransaction savedPaymentTransaction = getPaymentTransactionRepository().save(paymentTransaction);

            paymentRequest.get().setStatus(PaymentRequestStatus.PAID);
            PaymentRequest savedPaymentRequest = getPaymentRequestRepository().save(paymentRequest.get());

            System.out.println("[x] Payment request paid : " + savedPaymentRequest);
            System.out.println("[x] Payment transaction saved : " + savedPaymentTransaction);

            Optional<Wallet> freelancerWallet = getWalletRepository().findById(paymentRequest.get().getFreelancerId());
            if (freelancerWallet.isPresent()) {
                freelancerWallet.get().setBalance(freelancerWallet.get().getBalance() + paymentRequest.get().getAmount());
                Wallet savedWallet = getWalletRepository().save(freelancerWallet.get());

                System.out.println("[x] Wallet updated : " + savedWallet);

                WalletTransaction walletTransaction = WalletTransaction.builder()
                        .withWalletId(paymentRequest.get().getFreelancerId())
                        .withAmount(paymentRequest.get().getAmount())
                        .withPaymentTransactionId(savedPaymentTransaction.getId())
                        .withDescription(paymentRequest.get().getDescription())
                        .withTransactionType(WalletTransactionType.CREDIT)
                        .build();
                WalletTransaction savedWalletTransaction = getWalletTransactionRepository().save(walletTransaction);

                System.out.println("[x] Wallet transaction saved : " + savedWalletTransaction);
            } else {
                // TODO: Handle freelancer wallet not found
            }
            return PayPaymentRequestResponse.builder()
                    .withSuccess(true)
                    .withTransactionId(savedPaymentTransaction.getId())
                    .withTransactionStatus(PaymentTransactionStatus.SUCCESS)
                    .build();
        } catch (Exception e) {
            System.out.println("[x] Payment request failed : " + e.getMessage());
            // TODO: Handle payment transaction failure (Retry mechanism ?)
            return PayPaymentRequestResponse.builder()
                    .withSuccess(false)
                    .withTransactionId(null)
                    .withTransactionStatus(null)
                    .build();
        }
    }
}
