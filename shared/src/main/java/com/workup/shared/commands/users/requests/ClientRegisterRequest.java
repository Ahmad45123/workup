package com.workup.shared.commands.users.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = ClientRegisterRequest.ClientRegisterRequestBuilder.class)
public class ClientRegisterRequest {
    private String email;
    private String passwordHash;
    private String clientName;
    private String industry;
    private String city;
    private String photoId;
    private String description;
    private Integer employeeCount;
}
