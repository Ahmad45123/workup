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

  String email;
  String passwordHash;
  String fullName;
  String jobTitle;
  String city;
  Date birthDate;
}
