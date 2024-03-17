package com.workup.shared.commands.payments.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.payments.WalletTransaction;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = GetWalletTransactionsResponse.GetWalletTransactionsResponseBuilder.class)
public class GetWalletTransactionsResponse extends CommandResponse {
    private final WalletTransaction[] transactions;
}
