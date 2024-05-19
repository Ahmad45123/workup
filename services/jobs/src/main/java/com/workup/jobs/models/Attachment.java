package com.workup.jobs.models;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Getter
@Builder(setterPrefix = "with")
@UserDefinedType
public class Attachment implements Serializable {

  private String url;
  private String name;
}
