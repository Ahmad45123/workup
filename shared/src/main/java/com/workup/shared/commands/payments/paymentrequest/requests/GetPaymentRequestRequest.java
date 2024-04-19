package com.workup.shared.commands.payments.paymentrequest.requests;

import com.workup.shared.commands.CommandRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetPaymentRequestRequest extends CommandRequest {
    private final String paymentRequestId;
}
