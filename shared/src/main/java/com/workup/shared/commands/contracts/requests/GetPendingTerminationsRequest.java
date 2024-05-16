package com.workup.shared.commands.contracts.requests;

import com.workup.shared.commands.CommandRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

// PURPOSE : To fulfill the need for the read of CRUD for our terminations repository
@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
@Setter
public class GetPendingTerminationsRequest extends CommandRequest {
  private final String contractId;
}
