package com.workup.shared.commands.jobs.proposals.responses;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.jobs.proposals.ProposalModel;
import lombok.Getter;
import lombok.Builder;

import java.util.ArrayList;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = GetMyProposalsResponse.GetMyProposalsResponseBuilder.class)
public class GetMyProposalsResponse extends CommandResponse {
    private final ArrayList<ProposalModel> proposals;
}
