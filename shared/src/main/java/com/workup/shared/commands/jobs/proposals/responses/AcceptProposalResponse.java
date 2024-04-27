package com.workup.shared.commands.jobs.proposals.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.jobs.proposals.AcceptedJobInfo;
import com.workup.shared.commands.jobs.proposals.AcceptedProposalInfo;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@JsonSerialize
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class AcceptProposalResponse extends CommandResponse {

  private final String message;
  private final AcceptedJobInfo job;
  private final AcceptedProposalInfo proposal;
  private final String contractId;
}
