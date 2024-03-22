package com.workup.shared.commands.jobs;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;

@Builder(setterPrefix = "with")
@JsonDeserialize
public class JobListingItem {
    
}
