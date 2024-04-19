package com.workup.shared.commands.payments.dto;

import com.workup.shared.enums.payments.PaymentTransactionStatus;
import lombok.experimental.SuperBuilder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

@SuperBuilder(setterPrefix = "with")
@JsonDeserialize
public class PaymentTransactionDTO {
    private String id;
    private String paymentRequestId;
    private double amount;
    private Date createdAt;
    private Date updatedAt;
    private PaymentTransactionStatus status;
}
