package com.workup.jobs.models;

import java.io.Serializable;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Getter
@Builder(setterPrefix = "with")
@UserDefinedType
public class Milestone implements Serializable {

  private String description;
  private Date dueDate;
  private double amount;
}
