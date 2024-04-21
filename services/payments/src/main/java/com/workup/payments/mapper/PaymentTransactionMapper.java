package com.workup.payments.mapper;

import com.workup.payments.models.PaymentTransaction;
import com.workup.shared.commands.payments.dto.PaymentTransactionDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentTransactionMapper {

    private PaymentTransactionMapper() {
    }

    public static PaymentTransactionDTO mapToPaymentTransactionDTO(
            PaymentTransaction paymentTransaction
    ) {
        return PaymentTransactionDTO
                .builder()
                .withId(paymentTransaction.getId())
                .withPaymentRequestId(paymentTransaction.getPaymentRequestId())
                .withAmount(paymentTransaction.getAmount())
                .withCreatedAt(paymentTransaction.getCreatedAt())
                .withUpdatedAt(paymentTransaction.getUpdatedAt())
                .withStatus(paymentTransaction.getStatus())
                .build();
    }

    public static List<PaymentTransactionDTO> mapToPaymentTransactionDTOs(
            List<PaymentTransaction> paymentTransactions
    ) {
        return paymentTransactions
                .stream()
                .map(PaymentTransactionMapper::mapToPaymentTransactionDTO)
                .collect(Collectors.toList());
    }
}
