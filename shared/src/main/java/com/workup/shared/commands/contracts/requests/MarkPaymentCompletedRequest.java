package com.workup.shared.commands.contracts.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import lombok.Builder;
import lombok.Getter;

// PURPOSE: The purpose of this request is to transition the state of a milestone
// from ACCEPTED to PAID. This should be called by the payments microservice, and should only
// occur when the milestone was accepted and was then paid.
@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = com.workup.shared.commands.contracts.requests.MarkPaymentCompletedRequest.class)
public class MarkPaymentCompletedRequest extends CommandRequest {
    private final String milestoneId;
}
