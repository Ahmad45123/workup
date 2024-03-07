package com.workup.shared.commands.users.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = FreelancerProfileResponse.FreelancerProfileResponseBuilder.class)
public class FreelancerProfileResponse extends CommandResponse {
    String email;
    String full_name;
    String city;
    String job_title;
    String description;
    String birth_date;
    String[] languages;
    String[] skills;
}
