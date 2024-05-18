package com.workup.users.commands.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.users.db.Achievement;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetFreelancerAchievementsResponse extends CommandResponse {
  List<Achievement> achievements;
}
