package com.workup.shared.commands.contracts.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import lombok.Builder;
import lombok.Getter;

// PURPOSE: this request is to view the milestones for a contract, this can be
// done by either the client or the freelancer, to see the current state
// of the project, as well as how well each milestone is going.
@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = ViewContractMilestonesRequest.class)
public class ViewContractMilestonesRequest extends CommandRequest {
    private final String contractId;
}
