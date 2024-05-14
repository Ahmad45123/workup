package com.workup.shared.commands.contracts.requests;

import com.workup.shared.commands.CommandRequest;
import com.workup.shared.enums.contracts.TerminationRequestStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

// Purpose : The admin can review all the termination requests, and choose to accept/reject some of
// them based on each case
@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class HandleTerminationRequest extends CommandRequest {

  @Setter private String contractTerminationRequestId;
  private final TerminationRequestStatus chosenStatus;
}
