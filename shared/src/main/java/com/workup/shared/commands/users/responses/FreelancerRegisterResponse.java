package com.workup.shared.commands.users.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;
import lombok.experimental.SuperBuilder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class FreelancerRegisterResponse extends CommandResponse {
    boolean success;
    String authToken;
}
