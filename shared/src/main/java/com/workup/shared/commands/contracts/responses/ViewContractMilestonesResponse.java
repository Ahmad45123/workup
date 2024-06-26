package com.workup.shared.commands.contracts.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.contracts.Milestone;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class ViewContractMilestonesResponse extends CommandResponse {

  private final List<Milestone> contractMilestones;
}
