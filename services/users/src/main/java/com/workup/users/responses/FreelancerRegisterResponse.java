package com.workup.users.responses;

import com.workup.shared.commands.CommandResponse;
import lombok.Builder;

@Builder
public class FreelancerRegisterResponse extends CommandResponse {
    boolean success;
    String authToken;
}
