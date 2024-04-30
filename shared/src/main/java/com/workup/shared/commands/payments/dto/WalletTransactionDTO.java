package com.workup.shared.commands.payments.dto;

import com.workup.shared.enums.payments.WalletTransactionType;
import java.util.Date;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class WalletTransactionDTO {

  private String id;
  private String walletId;
  private double amount;
  private String paymentTransactionId;
  private String description;
  private Date createdAt;
  private WalletTransactionType transactionType;
}
