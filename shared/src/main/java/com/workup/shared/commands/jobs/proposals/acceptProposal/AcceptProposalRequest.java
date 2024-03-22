package com.workup.shared.commands.jobs.proposals.acceptProposal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.workup.shared.commands.CommandRequest;

public class AcceptProposalRequest extends CommandRequest {
    String jobId;
    String proposalId;

    @JsonCreator
    public AcceptProposalRequest(@JsonProperty("job_id") String jobId, @JsonProperty("proposal_id") String proposalId) {
        this.jobId = jobId;
        this.proposalId = proposalId;
    }
}
