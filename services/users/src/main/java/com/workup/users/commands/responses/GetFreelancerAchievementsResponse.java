package com.workup.users.commands.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.users.db.Achievement;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import java.util.List;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetFreelancerAchievementsResponse extends CommandResponse {
    List<Achievement> achievements;
}
