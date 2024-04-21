package com.workup.users.commands;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;

import lombok.experimental.SuperBuilder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class ClientSetProfileRequest extends CommandRequest {
    String user_id;
    String name;
    String email;
    String city;
    String description;
    String industry;
    Integer employee_count;
}
