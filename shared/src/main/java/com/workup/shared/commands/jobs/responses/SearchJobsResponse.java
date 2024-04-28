package com.workup.shared.commands.jobs.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.jobs.JobListingItem;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class SearchJobsResponse extends CommandResponse {

  private final JobListingItem[] jobs;
  private final int totalJobs;
  private final String pagingState;
}
