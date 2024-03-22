package com.workup.shared.commands.jobs.proposals.getMyProposals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.jobs.proposals.ProposalModel;

import java.util.ArrayList;

public class GetMyProposalsResponse extends CommandResponse {
    ArrayList<ProposalModel> proposals;

    @JsonCreator
    public GetMyProposalsResponse(@JsonProperty("proposals") ArrayList<ProposalModel> proposals) {
        this.proposals = proposals;
    }
}
