package com.workup.shared.commands.contracts.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

// PURPOSE: The purpose of this request is for the client or the freelancer
// to request terminating the contract, incase the contract was finished or if
// one of them does not wish to continue for any reason. This request will return a response
// as we discussed that there are some cases when auto-termination is valid(if the contract's
// milestones are finished or if the contract was established during the previous 3 days).
// Incase all the milestones are completed the state of the contract is then changed to
// COMPLETED. Otherwise it is changed to TERMINATED instead.

/*
    I think we better keep the termination logic completely for the `termination` (meaning one side doesn't like to continue the contract)
    and all the termination requests are to be reviewed by the admin, and then the amdin makes the decision about acc/rej these termination requests
 */
@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class ContractTerminationRequest extends CommandRequest {
    private final String userId;
    private final String contractId;
    private final String reason;
    private final Date date;
}
