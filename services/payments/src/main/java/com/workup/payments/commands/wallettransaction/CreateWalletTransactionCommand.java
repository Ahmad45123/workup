package com.workup.payments.commands.wallettransaction;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.models.WalletTransaction;
import com.workup.shared.commands.payments.wallettransaction.requests.CreateWalletTransactionRequest;
import com.workup.shared.commands.payments.wallettransaction.responses.CreateWalletTransactionResponse;

public class CreateWalletTransactionCommand extends PaymentCommand<CreateWalletTransactionRequest, CreateWalletTransactionResponse> {

    @Override
    public CreateWalletTransactionResponse Run(CreateWalletTransactionRequest request) {
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .withWalletId(request.getFreelancerId())
                .withAmount(request.getAmount())
                .withPaymentTransactionId(request.getPaymentTransactionId())
                .withDescription(request.getDescription())
                .withTransactionType(request.getTransactionType())
                .build();
        try {
            WalletTransaction savedWalletTransaction = getWalletTransactionRepository().save(walletTransaction);

            System.out.println("[x] Wallet transaction created : " + savedWalletTransaction);

            return CreateWalletTransactionResponse.builder()
                    .withSuccess(true)
                    .withWalletTransactionId(savedWalletTransaction.getId())
                    .build();
        } catch (Exception e) {
            System.out.println("[x] Wallet transaction creation failed : " + e.getMessage());

            return CreateWalletTransactionResponse.builder()
                    .withSuccess(false)
                    .withWalletTransactionId(null)
                    .build();
        }
    }

}
