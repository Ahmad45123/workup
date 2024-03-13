package com.workup.shared.commands.payments;

import lombok.Builder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Builder(setterPrefix = "with")
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
