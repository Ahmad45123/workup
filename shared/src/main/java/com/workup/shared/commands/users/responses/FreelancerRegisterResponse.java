package com.workup.shared.commands.users.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = FreelancerRegisterResponse.FreelancerRegisterResponseBuilder.class)
public class FreelancerRegisterResponse extends CommandResponse {
    boolean success;
    String authToken;
}
