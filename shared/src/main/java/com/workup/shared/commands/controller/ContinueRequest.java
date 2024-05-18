package com.workup.shared.commands.controller;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

/** Makes a service start accepting requests and acquire resources again. */
@Builder
@Jacksonized
public class ContinueRequest {
  // No fields are required?
}
