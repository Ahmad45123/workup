package com.workup.shared.commands.controller;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/** Sets the number of DB connections in the pool of a service. */
@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class SetMaxDBConnectionsRequest {
  int maxDBConnections;
}
