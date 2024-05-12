package com.workup.shared.commands.users.responses;

import com.workup.shared.commands.CommandResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class ClientGetProfileResponse extends CommandResponse {
  public String name;
  public String email;
  public String city;
  public String description;
  public String industry;
  public Integer employeeCount;
}
