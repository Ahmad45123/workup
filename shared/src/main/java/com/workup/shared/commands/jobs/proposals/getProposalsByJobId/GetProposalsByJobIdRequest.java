package com.workup.shared.commands.jobs.proposals.getProposalsByJobId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.workup.shared.commands.CommandRequest;


public class GetProposalsByJobIdRequest extends CommandRequest {
    public String jobId;

    @JsonCreator
    public GetProposalsByJobIdRequest(
            @JsonProperty("jobId") String jobId
    ) {
        this.jobId = jobId;
    }
}
