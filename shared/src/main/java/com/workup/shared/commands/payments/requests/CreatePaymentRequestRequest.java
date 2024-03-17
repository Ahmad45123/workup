package com.workup.shared.commands.payments.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = CreatePaymentRequestRequest.CreatePaymentRequestRequestBuilder.class)
public class CreatePaymentRequestRequest extends CommandRequest {
    private final String clientId;
    private final String freelancerId;
    private final double amount;
    private final String description;
}
