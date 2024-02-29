package com.workup.shared.commands.jobs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.workup.shared.commands.CommandRequest;

public class CreateProposalRequest implements CommandRequest {
    public String freelancerId;
    public String coverLetter;

    @JsonCreator
    public CreateProposalRequest(@JsonProperty("freelancerId") String freelancerId, @JsonProperty("coverLetter") String coverLetter) {
        this.freelancerId = freelancerId;
        this.coverLetter = coverLetter;
    }
}
