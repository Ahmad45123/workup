package com.workup.shared.commands.jobs.proposals.createProposal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateProposalResponse {
    public String id;

    @JsonCreator
    public CreateProposalResponse(@JsonProperty("id") String id) {
        this.id = id;
    }
}
