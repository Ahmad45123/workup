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
  private String jobTitle;
  private String jobId;
  private String proposalId;
  private String freelancerId;
  private String clientId;
  private List<String> milestonesIds;
  private ContractState status;
}
