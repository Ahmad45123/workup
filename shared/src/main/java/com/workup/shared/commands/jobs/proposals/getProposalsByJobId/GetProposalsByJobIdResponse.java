package com.workup.shared.commands.jobs.proposals.getProposalsByJobId;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.ArrayList;

public class GetProposalsByJobIdResponse {
    ArrayList<ProposalByJobId> proposals;

    @JsonCreator
    public GetProposalsByJobIdResponse(ArrayList<ProposalByJobId> proposals) {
        this.proposals = proposals;
    }
}
