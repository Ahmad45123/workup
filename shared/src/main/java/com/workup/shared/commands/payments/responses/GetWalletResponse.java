package com.workup.shared.commands.payments.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = GetWalletResponse.GetWalletResponseBuilder.class)
public class GetWalletResponse extends CommandResponse {
    private final double balance;
}
