package com.workup.shared.commands.users.requests;

import java.util.Date;

import com.workup.shared.commands.CommandRequest;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class FreelancerSetProfileRequest extends CommandRequest {
    public String user_id;
    public String email;
    public String full_name;
    public String city;
    public String job_title;
    public String description;
    public Date birth_date;
}