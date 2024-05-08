package com.workup.shared.commands.users.requests;

import com.workup.shared.commands.CommandRequest;
import java.util.Date;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class AddFreelancerEducationRequest extends CommandRequest {
  String freelancer_id;
  String school_name;
  String degree;
  Date education_start_date;
  String city;
  Date end_date;
  String major;
  String education_description;
  String grade;
}
