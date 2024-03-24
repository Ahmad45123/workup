package com.workup.shared.commands.jobs.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = GetJobByIdRequest.GetJobByIdRequestBuilder.class)
public class GetJobByIdRequest {
    private final String jobId;
}
