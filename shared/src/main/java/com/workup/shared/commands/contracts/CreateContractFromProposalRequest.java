package com.workup.shared.commands.contracts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.workup.shared.commands.CommandRequest;

public class CreateContractFromProposalRequest extends CommandRequest {
    public String clientId;
    public String freelancerId;
    public String proposalId;

    @JsonCreator
    public CreateContractFromProposalRequest(@JsonProperty("clientId") String clientId, @JsonProperty("freelancerId") String freelancerId, @JsonProperty("proposalId") String proposalId) {
        this.clientId = clientId;
        this.freelancerId = freelancerId;
        this.proposalId = proposalId;
    }
}
