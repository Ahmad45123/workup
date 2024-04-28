package com.workup.users.commands.requests;

import com.workup.shared.commands.CommandRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class RemoveFreelancerAchievementRequest extends CommandRequest {
    String freelancerId;
    String achievementId;
}
