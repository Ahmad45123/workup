package com.workup.users.requests;
import com.workup.shared.commands.CommandRequest;
import lombok.Builder;
import lombok.NonNull;

import java.util.Date;

@Builder
public class FreelancerRegisterRequest extends CommandRequest {
    @NonNull String email;
    @NonNull String passwordHash;
    @NonNull String fullName;
    String jobTitle;
    String city;
    Date birthDate;
}
