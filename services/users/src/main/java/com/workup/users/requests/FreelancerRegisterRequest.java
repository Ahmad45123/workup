package com.workup.users.requests;
import com.workup.shared.commands.CommandRequest;
import lombok.Builder;

import java.util.Date;

@Builder
public class FreelancerRegisterRequest extends CommandRequest {
    String email;
    String passwordHash;
    String fullName;
    String jobTitle;
    String city;
    Date birthDate;
}
