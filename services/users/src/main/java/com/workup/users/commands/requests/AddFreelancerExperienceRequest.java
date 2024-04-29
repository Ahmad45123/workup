package com.workup.users.commands.requests;

import com.workup.shared.commands.CommandRequest;
import com.workup.users.db.Experience;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class AddFreelancerExperienceRequest extends CommandRequest {
  private String freelancerId;
  private Experience newExperience;
}
