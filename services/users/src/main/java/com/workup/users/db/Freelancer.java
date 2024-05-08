package com.workup.users.db;

import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Builder(setterPrefix = "with")
@Getter
@Setter
@Document
public class Freelancer {
  @Id private ObjectId id;
  @Indexed private String email;

  private String password_hash;
  private Date created_at;
  private String full_name;
  private Date birthdate;
  private String resume_link;
  private String city;
  private String job_title;
  private String photo_link;
  private String description;
  private List<String> skills;
  private List<String> languages;

  @DocumentReference private List<Experience> experiences;
  @DocumentReference private List<Achievement> achievements;
  @DocumentReference private List<Education> educations;
}
