package com.workup.shared.commands;

import com.workup.shared.enums.HttpStatusCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(setterPrefix = "with")
public abstract class CommandResponse {

  private HttpStatusCode statusCode;
  private String errorMessage;
}
