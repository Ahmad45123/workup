package com.workup.users.db;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder(setterPrefix = "with")
@Getter
@Setter
@Document
public class Client {
  @Id private ObjectId id;
  @Indexed private String email;

  private String password_hash;
  private Date created_at;
  private String client_name;
  private String industry;
  private String city;
  private String photo_link;
  private String client_description;
  private Integer employee_count;
}
