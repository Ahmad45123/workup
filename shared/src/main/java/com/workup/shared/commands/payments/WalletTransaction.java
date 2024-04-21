package com.workup.shared.commands.payments;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
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
