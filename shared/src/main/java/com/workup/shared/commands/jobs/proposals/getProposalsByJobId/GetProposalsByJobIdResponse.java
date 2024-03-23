package com.workup.shared.commands.jobs.proposals.getProposalsByJobId;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.jobs.proposals.ProposalModel;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = GetProposalsByJobIdResponse.GetProposalsByJobIdResponseBuilder.class)
public class GetProposalsByJobIdResponse extends CommandResponse {
    private final ArrayList<ProposalModel> proposals;
}
