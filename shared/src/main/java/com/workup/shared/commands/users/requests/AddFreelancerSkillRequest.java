package com.workup.shared.commands.users.requests;

import com.workup.shared.commands.CommandRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class AddFreelancerSkillRequest extends CommandRequest {
  String user_id;
  String newSkill;
}
