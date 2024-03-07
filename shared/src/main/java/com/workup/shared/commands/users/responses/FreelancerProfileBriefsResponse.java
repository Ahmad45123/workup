package com.workup.shared.commands.users.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = FreelancerProfileBriefsResponse.FreelancerProfileBriefsResponseBuilder.class)
public class FreelancerProfileBriefsResponse extends CommandResponse {
    private FreelancerProfileBrief[] freelancers;

    @Getter
    @Builder(setterPrefix = "with")
    public static class FreelancerProfileBrief {
        private String user_id;
        private String full_name;
        private String email;
    }
}
