package com.workup.users.responses;

import com.workup.shared.commands.CommandResponse;
import lombok.NonNull;

public class FreelancerRegisterResponse extends CommandResponse {
    @NonNull boolean success;
    String authToken;
}
