package com.workup.shared.commands.users.requests;

import com.workup.shared.commands.CommandRequest;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class FreelancerSetResumeRequest extends CommandRequest {
    public String user_id;
    public String resume_encoded; // Base64 encoded photo
   
}
