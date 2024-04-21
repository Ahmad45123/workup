package com.workup.shared.commands.payments.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.enums.payments.WalletTransactionType;
import java.util.Date;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
@JsonDeserialize
public class WalletTransactionDTO {

  private String id;
  private String walletId;
  private double amount;
  private String paymentTransactionId;
  private String description;
  private Date createdAt;
  private WalletTransactionType transactionType;
}
