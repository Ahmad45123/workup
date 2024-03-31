package com.workup.shared.commands.jobs.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = GetJobByIdRequest.GetJobByIdRequestBuilder.class)
public class GetJobByIdRequest extends CommandRequest {
    private final String jobId;
}
