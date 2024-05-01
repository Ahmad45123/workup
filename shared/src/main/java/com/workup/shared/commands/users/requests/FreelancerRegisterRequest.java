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
  private String email;
  private String password;
  private String fullName;
  private String jobTitle;
  private String city;
  private Date birthDate;
}
