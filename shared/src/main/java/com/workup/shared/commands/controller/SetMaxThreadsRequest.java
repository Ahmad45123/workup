package com.workup.shared.commands.controller;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/** Set the maximum number of threads that a service can use. */
@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class SetMaxThreadsRequest {
  int maxThreads;
}
