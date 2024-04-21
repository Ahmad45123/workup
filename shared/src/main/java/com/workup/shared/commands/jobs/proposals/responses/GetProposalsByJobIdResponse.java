package com.workup.shared.commands.jobs.proposals.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.jobs.proposals.ProposalModel;
import java.util.ArrayList;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@JsonSerialize
@Jacksonized
public class GetProposalsByJobIdResponse extends CommandResponse {

  private final ArrayList<ProposalModel> proposals;
}
