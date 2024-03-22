package com.workup.shared.commands.jobs.proposals.getProposalsByJobId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.workup.shared.commands.CommandResponse;

import java.util.ArrayList;


public class GetProposalsByJobIdResponse extends CommandResponse {
    ArrayList<ProposalByJobId> proposals;

    @JsonCreator
    public GetProposalsByJobIdResponse(@JsonProperty("proposals") ArrayList<ProposalByJobId> proposals) {
        this.proposals = proposals;
    }
}
