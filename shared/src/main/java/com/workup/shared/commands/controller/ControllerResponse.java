package com.workup.shared.commands.controller;

import com.workup.shared.commands.CommandResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * The response that the controller gets after executing a command. Usually we do not need to send
 * any data back to the controller, so the status code and error message fields are enough.
 */
@SuperBuilder(setterPrefix = "with")
@Getter
@Jacksonized
public class ControllerResponse extends CommandResponse {}
