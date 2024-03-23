package com.workup.shared.commands.jobs.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.Enums.Jobs.Experience;
import com.workup.shared.commands.CommandRequest;

import lombok.Builder;
import lombok.Getter;
@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = CreateJobRequest.CreateJobRequestBuilder.class)
public class CreateJobRequest extends CommandRequest {
    private final String title;
    private final String description;
    private final String location;
    private final double budget;
    private final String[] skills;
    private final Experience experience;
    private final String clientId;
}
