package com.workup.shared.commands.payments.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.payments.PaymentTransaction;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = GetClientPaymentTransactionsResponse.GetClientPaymentTransactionsResponseBuilder.class)
public class GetClientPaymentTransactionsResponse extends CommandResponse {
    private final PaymentTransaction[] transactions;
}
