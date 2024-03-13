package com.workup.shared.commands.payments;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;

@Builder(setterPrefix = "with")
@JsonDeserialize
public class WalletTransaction {
    private String id;
    private String walletId;
    private double amount;
    private String paymentTransactionId;
    private String description;
    private Date createdAt;
    private String transactionType;
}