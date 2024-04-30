package com.workup.shared.commands.jobs.requests;

import com.workup.shared.commands.CommandRequest;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class SearchJobsRequest extends CommandRequest {

  private final String query;
  private final int pageLimit;

  @Default private final String pagingState = null;
}
