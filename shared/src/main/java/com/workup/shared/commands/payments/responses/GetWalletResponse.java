package com.workup.shared.commands.payments.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.payments.requests.GetWalletRequest;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = GetWalletRequest.GetWalletRequestBuilder.class)
public class GetWalletResponse extends CommandResponse {
    private final double balance;
}
