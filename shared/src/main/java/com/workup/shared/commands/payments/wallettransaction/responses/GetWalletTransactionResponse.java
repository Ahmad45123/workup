package com.workup.shared.commands.payments.wallettransaction.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.payments.dto.WalletTransactionDTO;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetWalletTransactionResponse extends CommandResponse {
    private final WalletTransactionDTO transaction;
}
