package com.workup.shared.commands.contracts.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.shared.enums.contracts.MilestoneState;
import java.util.Date;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

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
