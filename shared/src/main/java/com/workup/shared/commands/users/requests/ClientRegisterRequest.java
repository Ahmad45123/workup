package com.workup.shared.commands.users.requests;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
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
