package com.workup.shared.commands;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
@Getter
public abstract class CommandRequest {

  private String userId;
}
