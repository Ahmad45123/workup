package com.workup.shared.commands.jobs.proposals.getProposalsByJobId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class GetProposalsByJobIdRequest {
    public String jobId;

    @JsonCreator
    public GetProposalsByJobIdRequest(
            @JsonProperty("jobId") String jobId
    ) {
        this.jobId = jobId;
    }
}
