package com.workup.shared.commands.contracts.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import lombok.Builder;
import lombok.Getter;

// PURPOSE: The purpose of this request is for the client or the freelancer
// to request terminating the contract, incase the contract was finished or if
// one of them does not wish to continue for any reason. This request will return a response
// as we discussed that there are some cases when auto-termination is valid(if the contract's
// milestones are finished or if the contract was established during the previous 3 days).
// Incase all the milestones are completed the state of the contract is then changed to
// COMPLETED. Otherwise it is changed to TERMINATED instead.
@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = com.workup.shared.commands.contracts.requests.ContractTerminationRequest.class)
public class ContractTerminationRequest extends CommandRequest {
    private final String userId;
    private final String contractId;
}
