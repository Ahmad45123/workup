package com.workup.shared.commands.users.responses;

import com.workup.shared.commands.CommandResponse;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class FreelancerGetPhotoResponse extends CommandResponse {
    public String photoEncoded; // Base64 encoded photo
}