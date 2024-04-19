package com.workup.shared.commands.jobs;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.enums.jobs.Experience;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
@Getter
@JsonDeserialize
public class JobListingItem {
    private final String id;
    private final String title;
    private final String description;
    private final Experience experience;
    private final String[] skills;
}
