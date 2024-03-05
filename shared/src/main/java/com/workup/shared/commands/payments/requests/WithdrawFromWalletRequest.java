package com.workup.shared.commands.payments.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = WithdrawFromWalletRequest.WithdrawFromWalletRequestBuilder.class)
public class WithdrawFromWalletRequest extends CommandRequest {
    private final String freelancerId;
    private final double amount;
    private final String description;
}