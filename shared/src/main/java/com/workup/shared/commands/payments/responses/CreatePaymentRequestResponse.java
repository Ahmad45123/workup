package com.workup.shared.commands.payments.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = CreatePaymentRequestResponse.CreatePaymentRequestResponseBuilder.class)
public class CreatePaymentRequestResponse {
    private final String paymentRequestId;
}
