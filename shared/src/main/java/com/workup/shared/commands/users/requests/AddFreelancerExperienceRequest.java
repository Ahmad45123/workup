package com.workup.shared.commands.users.requests;

import com.workup.shared.commands.CommandRequest;
import java.util.Date;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class AddFreelancerExperienceRequest extends CommandRequest {
  String company_name;
  String job_title;
  Date employment_start;
  Date employment_end;
  String experience_description;
  String city;
}
