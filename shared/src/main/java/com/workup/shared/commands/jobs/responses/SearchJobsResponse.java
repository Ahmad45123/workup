package com.workup.shared.commands.jobs.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.jobs.JobListingItem;

import lombok.experimental.SuperBuilder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class SearchJobsResponse extends CommandResponse {
    private final JobListingItem[] jobs;
    private final int totalJobs;
    private final int page;
    private final int pageSize;
}
