package com.workup.shared.commands.payments.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = WithdrawFromWalletResponse.WithdrawFromWalletResponseBuilder.class)
public class WithdrawFromWalletResponse extends CommandResponse {
    private final double balance;
    private final String withdrawalStatus;
}
