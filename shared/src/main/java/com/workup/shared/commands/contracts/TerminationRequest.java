package com.workup.shared.commands.contracts;

import com.workup.shared.enums.contracts.TerminationRequestStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class TerminationRequest {

  private final String requestId;

  private final String contractId;
  private final String requesterId;
  private final String reason;

  private final TerminationRequestStatus status;
}
