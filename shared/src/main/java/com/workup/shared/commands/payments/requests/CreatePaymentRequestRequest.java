package com.workup.shared.commands.payments.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class CreatePaymentRequestRequest extends CommandRequest {

  private final String clientId;
  private final String freelancerId;
  private final double amount;
  private final String description;
}
