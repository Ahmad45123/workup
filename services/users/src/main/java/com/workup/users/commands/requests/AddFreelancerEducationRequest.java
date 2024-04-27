package com.workup.users.commands.requests;

import com.workup.shared.commands.CommandRequest;
import com.workup.users.db.Education;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class AddFreelancerEducationRequest extends CommandRequest {
    String freelancer_id;
    Education newEducation;
}
