package com.workup.shared.commands.payments.wallet.requests;

import com.workup.shared.commands.CommandRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class CreateWalletRequest extends CommandRequest {

  private final String freelancerId;
}
