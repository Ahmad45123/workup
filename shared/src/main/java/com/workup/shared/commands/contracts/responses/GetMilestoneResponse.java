package com.workup.shared.commands.contracts.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.contracts.Milestone;
import com.workup.shared.enums.contracts.MilestoneState;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;
import java.util.UUID;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetMilestoneResponse extends CommandResponse {
  private final String milestoneId;

  private final String contractId;

  private final String description;
  private final Date dueDate;
  private final double amount;
  private final MilestoneState status;
}
