package com.workup.shared.commands.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/** Set the severity level of an error to be reported. */
@Getter
@Builder(setterPrefix = "with")
@Jacksonized
public class SetLoggingLevelRequest {
  String level;
}
