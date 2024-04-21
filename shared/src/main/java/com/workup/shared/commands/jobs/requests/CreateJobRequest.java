package com.workup.shared.commands.jobs.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.enums.jobs.Experience;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class CreateJobRequest extends CommandRequest {

  private final String title;
  private final String description;
  private final String location;
  private final double budget;
  private final String[] skills;
  private final Experience experience;
}
