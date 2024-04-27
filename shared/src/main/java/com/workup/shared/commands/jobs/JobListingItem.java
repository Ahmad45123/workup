package com.workup.shared.commands.jobs;

import com.workup.shared.enums.jobs.Experience;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder(setterPrefix = "with")
@Getter
@Jacksonized
public class JobListingItem {

  private final String id;
  private final String title;
  private final String description;
  private final Experience experience;
  private final String[] skills;
}
