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
public class ExperienceView {
  private String company_name;
  private String job_title;
  private Date employment_start;
  private Date employment_end;
  private String experience_description;
  private String city;
}
