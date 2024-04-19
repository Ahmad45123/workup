package com.workup.shared.commands.payments.paymentrequest.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.payments.dto.PaymentRequestDTO;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetClientPaymentRequestsResponse extends CommandResponse {
    private final List<PaymentRequestDTO> requests;
}
