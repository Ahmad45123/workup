package com.workup.shared.commands.users.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = FreelancerProfileRequest.FreelancerProfileRequestBuilder.class)
public class FreelancerProfileRequest extends CommandRequest {
    String user_id;
}
