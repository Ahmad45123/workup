package com.workup.shared.commands.contracts.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.contracts.Milestone;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = ViewContractMilestonesResponse.class)
public class ViewContractMilestonesResponse extends CommandResponse {
    private final Milestone[] contractMilestones;
}
