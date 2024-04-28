package com.workup.users.commands.requests;

import com.workup.shared.commands.CommandRequest;
import com.workup.users.db.Achievement;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class UpdateFreelancerAchievementRequest extends CommandRequest {
    String freelancer_id;
    Achievement updatedAchievement;
}
