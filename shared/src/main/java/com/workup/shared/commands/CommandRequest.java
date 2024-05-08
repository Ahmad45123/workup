package com.workup.shared.commands;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
@Getter
public abstract class CommandRequest {
  @Setter private String userId;
}
