package com.workup.shared.commands.jobs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.workup.shared.commands.CommandRequest;

public class CreateJobRequest extends CommandRequest {
    public String clientId;
    public String title;
    public String description;

    @JsonCreator
    public CreateJobRequest(@JsonProperty("clientId") String clientId, @JsonProperty("title") String title, @JsonProperty("description") String description) {
        this.clientId = clientId;
        this.title = title;
        this.description = description;
    }
}
