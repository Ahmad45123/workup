package com.workup.shared.views.users;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Builder(setterPrefix = "with")
@Jacksonized
public class EducationView {
  private String school_name;
  private String degree;
  private Date education_start_date;
  private String city;
  private Date end_date;
  private String major;
  private String education_description;
  private String grade;
}
