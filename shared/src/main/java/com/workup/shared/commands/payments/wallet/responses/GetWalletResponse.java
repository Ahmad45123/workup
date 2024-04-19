package com.workup.shared.commands.payments.wallet.responses;

import com.workup.shared.commands.CommandResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetWalletResponse extends CommandResponse {
    private final double balance;
}
