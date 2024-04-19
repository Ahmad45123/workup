package com.workup.payments.commands.paymenttransaction;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.mapper.PaymentTransactionMapper;
import com.workup.payments.models.PaymentTransaction;
import com.workup.shared.commands.payments.dto.PaymentTransactionDTO;
import com.workup.shared.commands.payments.paymenttransaction.requests.GetFreelancerPaymentTransactionsRequest;
import com.workup.shared.commands.payments.paymenttransaction.responses.GetFreelancerPaymentTransactionsResponse;

import java.util.List;

public class GetFreelancerPaymentTransactionsCommand extends PaymentCommand<GetFreelancerPaymentTransactionsRequest, GetFreelancerPaymentTransactionsResponse> {

    @Override
    public GetFreelancerPaymentTransactionsResponse Run(GetFreelancerPaymentTransactionsRequest request) {
        List<PaymentTransaction> savedTransactions = getPaymentTransactionRepository()
                .findAllByFreelancerId(request.getFreelancerId());
        List<PaymentTransactionDTO> paymentTransactionDTOS = PaymentTransactionMapper.mapToPaymentTransactionDTOs(savedTransactions);

        System.out.println("[x] Payment transactions fetched : " + paymentTransactionDTOS);

        return GetFreelancerPaymentTransactionsResponse.builder()
                .withSuccess(true)
                .withTransactions(paymentTransactionDTOS)
                .build();
    }
}
