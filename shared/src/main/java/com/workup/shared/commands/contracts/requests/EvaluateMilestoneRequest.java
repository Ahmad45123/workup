package com.workup.shared.commands.contracts.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.enums.contracts.MilestoneState;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

// PURPOSE: The purpose of this request is for the client to evaluate the progress made
// for a certain milestone. For this endpoint to be valid, the freelancer must have progressed the
// milestone to the IN_REVIEW state. The client will then choose the next state the milestone should progress
// to from two possible choices(the first choice is back to the IN_PROGRESS state if they want changed
// to be made, otherwise the choice is to the ACCEPTED state if they accept the work done for this milestone).
// if a milestone is ACCEPTED, it cannot be evaluated or progressed again, except to the PAID state by the
// mark payment request.
@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class EvaluateMilestoneRequest extends CommandRequest {
    private final String clientId;
    private final String milestoneId;
    private final MilestoneState evaluatedState;
}
