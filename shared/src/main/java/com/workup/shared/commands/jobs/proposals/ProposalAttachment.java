package com.workup.shared.commands.jobs.proposals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ProposalAttachment(String name, String url) {
    @JsonCreator
    public ProposalAttachment(@JsonProperty("name") String name, @JsonProperty("url") String url) {
        this.name = name;
        this.url = url;
    }
}
