package com.workup.shared.commands.jobs.proposals.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.jobs.proposals.ProposalModel;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@JsonSerialize
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetMyProposalsResponse extends CommandResponse {

  private final List<ProposalModel> proposals;
}
