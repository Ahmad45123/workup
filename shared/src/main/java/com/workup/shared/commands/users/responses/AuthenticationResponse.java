package com.workup.shared.commands.users.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = AuthenticationResponse.AuthenticationResponseBuilder.class)
public class AuthenticationResponse {
    private String email;
    private UserType userType;
}
