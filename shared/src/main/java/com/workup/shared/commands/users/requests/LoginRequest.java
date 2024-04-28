package com.workup.shared.commands.users.requests;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class LoginRequest {
  private String email;
  private String passwordHash;
}
