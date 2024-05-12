package com.workup.shared.commands.users.requests;

import com.workup.shared.commands.CommandRequest;
import java.util.Date;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class FreelancerSetProfileRequest extends CommandRequest {
  public String email;
  public String fullName;
  public String city;
  public String jobTitle;
  public String description;
  public Date birthDate;
}
