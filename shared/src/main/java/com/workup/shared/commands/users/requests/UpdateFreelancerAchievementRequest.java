package com.workup.shared.commands.users.requests;

import com.workup.shared.commands.CommandRequest;
import java.util.Date;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class UpdateFreelancerAchievementRequest extends CommandRequest {
  String freelancer_id;
  String achievement_id;
  String new_achievement_name;
  String new_awarded_by;
  String new_achievement_description;
  Date new_award_date;
}
