package com.workup.shared.commands.jobs.proposals.acceptProposal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.workup.shared.commands.CommandResponse;

public class AcceptProposalResponse extends CommandResponse {
    public String message;
    // I modified the response type as there is no need to return the
    // status of the job or the proposal, as this will require creating a
    // class with parameters (id, status) which is not so convenient.
    public String jobId;
    public String proposalId;

    @JsonCreator
    public AcceptProposalResponse(@JsonProperty("message") String message,
                                  @JsonProperty("job_id") String jobId,
                                  @JsonProperty("proposal_id") String proposalId) {
        this.message = message;
        this.jobId = jobId;
        this.proposalId = proposalId;
    }
}
