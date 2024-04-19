package com.workup.shared.commands.contracts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.enums.contracts.MilestoneState;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class Milestone {

    private final String milestoneId; // Not Needed when initiating contract, make it null
    private final String contractId; // Not needed when initiating contract, make it null

    private final String description;
    private final String dueDate;
    private final String amount;
    private final MilestoneState status; // Not needed with initiating contract, make it null
}
