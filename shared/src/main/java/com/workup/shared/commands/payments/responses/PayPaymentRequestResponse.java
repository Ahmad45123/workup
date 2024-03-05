package com.workup.shared.commands.payments.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = PayPaymentRequestResponse.PayPaymentRequestResponseBuilder.class)
public class PayPaymentRequestResponse extends CommandResponse {
    private final String transaction_id;
    private final String transaction_status;
}
