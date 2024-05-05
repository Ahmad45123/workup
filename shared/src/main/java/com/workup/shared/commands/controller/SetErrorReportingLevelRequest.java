package com.workup.shared.commands.controller;

import com.workup.shared.enums.ErrorLevel;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/** Set the severity level of an error to be reported. */
@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class SetErrorReportingLevelRequest {
  ErrorLevel errorLevel;
}