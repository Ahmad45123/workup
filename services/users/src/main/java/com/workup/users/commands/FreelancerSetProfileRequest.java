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
public class FreelancerSetProfileRequest extends CommandRequest {
    String user_id;
    String email;
    String full_name;
    String city;
    String job_title;
    String description;
    Date birth_date;
}
