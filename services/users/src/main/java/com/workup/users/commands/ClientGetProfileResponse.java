package com.workup.users.commands;

import com.workup.shared.commands.CommandResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class ClientGetProfileResponse extends CommandResponse {
  String name;
  String email;
  String city;
  String description;
  String industry;
  Integer employee_count;
}
