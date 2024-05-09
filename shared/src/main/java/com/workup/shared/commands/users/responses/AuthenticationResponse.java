package com.workup.shared.commands.users.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.shared.enums.users.UserType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class AuthenticationResponse extends CommandResponse {
  private String email;
  private UserType userType;
}
