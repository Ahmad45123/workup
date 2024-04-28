package com.workup.shared.commands.payments.wallettransaction.responses;

import com.workup.shared.commands.CommandResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class CreateWalletTransactionResponse extends CommandResponse {

  private final String walletTransactionId;
}
