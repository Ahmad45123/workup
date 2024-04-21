package com.workup.shared.commands.payments.wallettransaction.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.payments.dto.WalletTransactionDTO;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetWalletTransactionsResponse extends CommandResponse {
    private final List<WalletTransactionDTO> transactions;
}
