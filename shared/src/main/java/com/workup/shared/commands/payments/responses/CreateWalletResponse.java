package com.workup.shared.commands.payments.responses;

import com.workup.shared.commands.CommandResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = CreateWalletResponse.CreateWalletResponseBuilder.class)
public class CreateWalletResponse extends CommandResponse {
}
