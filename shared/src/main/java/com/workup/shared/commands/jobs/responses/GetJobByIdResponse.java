package com.workup.shared.commands.jobs.responses;


import java.sql.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.enums.jobs.Experience;

import lombok.Builder;
import lombok.Getter;
@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = GetJobByIdResponse.GetJobByIdResponseBuilder.class)
public class GetJobByIdResponse {
    private final String id;
    private final String title;
    private final String description;
    private final String location;
    private final double budget;
    private final String[] skills;
    private final Experience experience;
    private final String clientId;
    private final boolean isActive;
    private final Date createdAt;
    private final Date modifiedAt;
}
