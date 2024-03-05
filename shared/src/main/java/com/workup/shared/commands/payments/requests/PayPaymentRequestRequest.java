package com.workup.shared.commands.payments.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = PayPaymentRequestRequest.PayPaymentRequestRequestBuilder.class)
public class PayPaymentRequestRequest extends CommandRequest {
    private final String payment_request_id;
}
