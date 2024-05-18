package com.workup.shared.commands.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/** Deletes a command from the commands map. */
@Jacksonized
@Builder
@Getter
public class DeleteCommandRequest {
  String commandName;
}
