package com.workup.shared.commands.contracts;

import com.workup.shared.enums.contracts.MilestoneState;
import java.util.Date;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class Milestone {

  private final String milestoneId; // Not needed when initiating contract, make it null
  private final String contractId; // Not needed when initiating contract, make it null

  private final String description;
  private final Date dueDate;
  private final double amount;
  private final MilestoneState status; // Not needed with initiating contract, make it null
}
