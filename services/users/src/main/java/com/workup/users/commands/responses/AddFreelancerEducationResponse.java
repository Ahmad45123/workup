package com.workup.shared.commands.users.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.users.db.Freelancer;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class AddFreelancerEducationResponse extends CommandResponse {
    Freelancer freelancer;
}
