package com.workup.shared.commands.payments;

import lombok.experimental.SuperBuilder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@SuperBuilder(setterPrefix = "with")
@JsonDeserialize
public class PaymentTransaction {
    private String id;
    private String paymentRequestId;
    private double amount;
    private String externalTransactionId;
    private String paymentGateway;
    private String createdAt;
    private String status;
}
