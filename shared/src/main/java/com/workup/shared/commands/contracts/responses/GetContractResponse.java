package com.workup.shared.commands.contracts.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.shared.enums.contracts.ContractState;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetContractResponse extends CommandResponse {
  private final String contractId;
  private final String jobTitle;
  private final String jobId;
  private final String proposalId;
  private final String freelancerId;
  private final String clientId;
  private final List<String> milestonesIds;
  private final ContractState status;
}
