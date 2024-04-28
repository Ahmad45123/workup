package com.workup.shared.commands.users.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = FreelancerRegisterRequest.FreelancerRegisterRequestBuilder.class)
public class FreelancerRegisterRequest extends CommandRequest {
    private String email;
    private String passwordHash;
    private String fullName;
    private String jobTitle;
    private String city;
    private Date birthDate;
}
