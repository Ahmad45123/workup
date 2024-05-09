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
@Document(collection = "Education")
public class Education {
  @Id private ObjectId id;

  private String school_name;
  private String degree;
  private Date education_start_date;
  private String city;
  private Date end_date;
  private String major;
  private String education_description;
  private String grade;
}
