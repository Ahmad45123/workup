package com.workup.shared.commands.contracts.requests;

import com.workup.shared.commands.CommandRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

// PURPOSE: This request is for the freelancer to progress the state of a milestone. The first
// time this request is called, the internal state of a milestone is moved from OPEN to IN_PROGRESS
// afterward if it is called in the IN_PROGRESS state it is then move to the IN_REVIEW state.
// Calling this
// endpoint when the milestone is in any other state has no effect.
@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class ProgressMilestoneRequest extends CommandRequest {
  @Setter private String milestoneId;
}
