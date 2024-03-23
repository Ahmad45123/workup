package com.workup.shared.commands.jobs.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;

import lombok.Builder;

import lombok.Getter;

@Getter
@Builder
@JsonDeserialize(builder = CreateJobResponse.CreateJobResponseBuilder.class)
public class CreateJobResponse extends CommandResponse{
    private final String jobId;
}
