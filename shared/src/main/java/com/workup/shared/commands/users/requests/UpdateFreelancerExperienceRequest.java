package com.workup.shared.commands.users.requests;

import com.workup.shared.commands.CommandRequest;
import java.util.Date;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class UpdateFreelancerExperienceRequest extends CommandRequest {
  String freelancer_id;
  String experience_id;
  String new_company_name;
  String new_job_title;
  Date new_employment_start;
  Date new_employment_end;
  String new_experience_description;
  String new_city;
}
