package com.workup.shared.commands.jobs.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;

import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import lombok.Getter;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class CreateJobResponse extends CommandResponse{
    private final String jobId;
}
