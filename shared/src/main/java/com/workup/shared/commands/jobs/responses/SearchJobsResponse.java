package com.workup.shared.commands.jobs.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.jobs.JobListingItem;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = SearchJobsResponse.SearchJobsResponseBuilder.class)
public class SearchJobsResponse {
    private final JobListingItem[] jobIds;
    private final int totalJobs;
    private final int page;
    private final int pageSize;
}
