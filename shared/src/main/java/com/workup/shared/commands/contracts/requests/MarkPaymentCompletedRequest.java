package com.workup.shared.commands.contracts.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

// PURPOSE: The purpose of this request is to transition the state of a milestone
// from ACCEPTED to PAID. This should be called by the payments microservice, and should only
// occur when the milestone was accepted and was then paid.
@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class MarkPaymentCompletedRequest extends CommandRequest {

  private final String milestoneId;
}
