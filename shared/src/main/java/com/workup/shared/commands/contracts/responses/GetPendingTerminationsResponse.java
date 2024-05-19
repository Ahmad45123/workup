package com.workup.shared.commands.contracts.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.contracts.TerminationRequest;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetPendingTerminationsResponse extends CommandResponse {
  private List<TerminationRequest> terminationRequests;
}
