package com.workup.shared.commands.jobs.proposals.getMyProposals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.workup.shared.commands.CommandRequest;

public class GetMyProposalsRequest extends CommandRequest {
    @JsonCreator
    public GetMyProposalsRequest() {
        // The proposals are fetched based on the currently logged in user
    }
}
