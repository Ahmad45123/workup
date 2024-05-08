package com.workup.shared.commands.users.requests;

import com.workup.shared.commands.CommandRequest;
import java.util.Date;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class UpdateFreelancerEducationRequest extends CommandRequest {
  String education_id;
  String new_school_name;
  String new_degree;
  Date new_education_start_date;
  String new_city;
  Date new_end_date;
  String new_major;
  String new_education_description;
  String new_grade;
}
