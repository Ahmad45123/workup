package com.workup.shared.commands.jobs.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.jobs.JobListingItem;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder(setterPrefix = "with")
@Getter
@Jacksonized
public class GetMyJobsResponse extends CommandResponse{
    private final JobListingItem[] jobs;
}
