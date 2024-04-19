package com.workup.shared.commands.contracts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.enums.contracts.MilestoneState;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class Milestone {

    private final String milestoneId;
    private final String contractId;

    private final String description;
    private final String dueDate;
    private final String amount;
    private final MilestoneState status;
}
