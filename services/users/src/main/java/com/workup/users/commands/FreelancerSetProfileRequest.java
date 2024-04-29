package com.workup.users.commands;

import com.workup.shared.commands.CommandRequest;
import java.util.Date;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class FreelancerSetProfileRequest extends CommandRequest {
  String user_id;
  String email;
  String full_name;
  String city;
  String job_title;
  String description;
  Date birth_date;
}
