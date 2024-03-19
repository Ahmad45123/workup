package com.workup.shared.commands.jobs.proposals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class CreateProposalResponse {
    public String id;

    @JsonCreator
    public CreateProposalResponse(@JsonProperty("id") String id) {
        this.id = id;
    }
}
