package com.workup.shared.commands.payments.paymenttransaction.requests;

import com.workup.shared.commands.CommandRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetClientPaymentTransactionsRequest extends CommandRequest {

  private final String clientId;
}
