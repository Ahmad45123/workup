package com.workup.shared.commands.controller;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

/** Makes a service stop accepting requests and release resources. */
@Builder
@Jacksonized
public class FreezeRequest {
  // No fields are required?
}
