package com.workup.shared.commands.users.responses;

import com.workup.shared.commands.CommandResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetFreelancerSkillsResponse extends CommandResponse {
    List<String> skills;
}
