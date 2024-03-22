package com.workup.shared.commands.jobs.proposals.createProposal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.workup.shared.commands.CommandResponse;

public class CreateProposalResponse extends CommandResponse {
    public String id;

    @JsonCreator
    public CreateProposalResponse(@JsonProperty("id") String id) {
        this.id = id;
    }
}
