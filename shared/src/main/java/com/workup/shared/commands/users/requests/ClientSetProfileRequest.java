package com.workup.shared.commands.users.requests;

import com.workup.shared.commands.CommandRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class ClientSetProfileRequest extends CommandRequest {
  public String user_id;
  public String name;
  public String email;
  public String city;
  public String description;
  public String industry;
  public Integer employee_count;
}
