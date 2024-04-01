package com.workup.shared.commands.contracts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.enums.contracts.MilestoneState;
import lombok.Builder;

@Builder(setterPrefix = "with")
@JsonDeserialize(builder = com.workup.shared.commands.contracts.Milestone.class)
public class Milestone {
    private final String milestoneId;
    private final String contractId;

    private final String description;
    private final String dueDate;
    private final String amount;
    private final MilestoneState status;

}
