package com.workup.shared.commands.jobs.proposals;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = AcceptedJobInfo.AcceptedJobInfoBuilder.class)
public class AcceptedJobInfo {
    private final String id;
    private final boolean isActive;
}
