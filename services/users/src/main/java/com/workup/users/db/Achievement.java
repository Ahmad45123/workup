package com.workup.users.db;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder(setterPrefix = "with")
@Getter
@Setter
@Document(collection = "Achievements")
public class Achievement {
  @Id private ObjectId id;

  private String achievement_name;
  private String awarded_by;
  private String achievement_description;
  private Date award_date;
}
