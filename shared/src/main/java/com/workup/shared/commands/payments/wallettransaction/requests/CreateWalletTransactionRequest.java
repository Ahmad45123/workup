package com.workup.shared.commands.payments.wallettransaction.requests;

import com.workup.shared.commands.CommandRequest;
import com.workup.shared.enums.payments.WalletTransactionType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class CreateWalletTransactionRequest extends CommandRequest {

  private final String freelancerId;
  private final double amount;
  private final String paymentTransactionId;
  private final String description;
  private final WalletTransactionType transactionType;
}
