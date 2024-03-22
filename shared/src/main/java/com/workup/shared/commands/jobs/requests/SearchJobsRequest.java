package com.workup.shared.commands.jobs.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = SearchJobsRequest.SearchJobsRequestBuilder.class)
public class SearchJobsRequest {
    private final int page;
    private final int pageSize;
    private final String query;
}
