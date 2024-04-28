package com.workup.shared.commands.users.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = AuthenticationRequest.AuthenticationRequestBuilder.class)
public class AuthenticationRequest {
    private String authToken;
}
