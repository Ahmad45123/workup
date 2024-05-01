package com.workup.shared.commands.users.requests;

import com.workup.shared.commands.CommandRequest;
import java.util.Date;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class FreelancerRegisterRequest extends CommandRequest {
  public String email;
  public String passwordHash;
  public String fullName;
  public String jobTitle;
  public String city;
  public Date birthDate;
}
