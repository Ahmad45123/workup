package com.workup.users.commands;

import com.workup.shared.commands.CommandRequest;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class ClientGetProfileRequest extends CommandRequest {
    String user_id;
   
}