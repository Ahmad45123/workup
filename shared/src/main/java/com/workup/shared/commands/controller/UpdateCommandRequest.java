package com.workup.shared.commands.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/** Updates the logic of a certain command. */
@Getter
@Builder(setterPrefix = "with")
@Jacksonized
public class UpdateCommandRequest {
  String commandName;
  byte[] byteCode;
}
