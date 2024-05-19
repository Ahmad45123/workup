package com.workup.shared.commands.controller;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/** Sets the name of the message queue the service is listening to. */
@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class SetMessageQueueRequest {
  String host;
  int port;
}
