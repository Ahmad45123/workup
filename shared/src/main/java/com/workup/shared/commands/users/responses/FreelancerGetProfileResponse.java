package com.workup.shared.commands.users.responses;

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
  public String email;
  public String fullName;
  public String city;
  public String jobTitle;
  public String description;
  public Date birthDate;
  public List<String> languages;
  public List<String> skills;
}
