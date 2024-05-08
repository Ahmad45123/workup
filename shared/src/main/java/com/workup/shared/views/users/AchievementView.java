package com.workup.shared.views.users;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Builder(setterPrefix = "with")
@Getter
@Setter
@Jacksonized
public class AchievementView {
  private String achievement_name;
  private String achievement_description;
  private Date award_date;
  private String awarded_by;
}
