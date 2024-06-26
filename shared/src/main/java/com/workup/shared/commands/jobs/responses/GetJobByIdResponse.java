package com.workup.shared.commands.jobs.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.shared.enums.jobs.Experience;
import java.util.Date;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetJobByIdResponse extends CommandResponse {

  private final String id;
  private final String title;
  private final String description;
  private final String location;
  private final double budget;
  private final String[] skills;
  private final Experience experience;
  private final String clientId;
  private final boolean active;
  private final Date createdAt;
  private final Date modifiedAt;
}
