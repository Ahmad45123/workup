package com.workup.shared.commands.users.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import lombok.experimental.SuperBuilder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class FreelancerRegisterRequest extends CommandRequest {
    public String email;
    public String passwordHash;
    public String fullName;
    public String jobTitle;
    public String city;
    public Date birthDate;
}
