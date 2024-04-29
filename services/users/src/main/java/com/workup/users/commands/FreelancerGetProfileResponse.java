package com.workup.users.commands;

import com.workup.shared.commands.CommandResponse;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class FreelancerGetProfileResponse extends CommandResponse {
  String email;
  String full_name;
  String city;
  String job_title;
  String description;
  Date birth_date;
  List<String> languages;
  List<String> skills;
}
