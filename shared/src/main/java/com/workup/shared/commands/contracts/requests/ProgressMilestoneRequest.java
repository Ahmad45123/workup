package com.workup.shared.commands.contracts.requests;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import lombok.Builder;
import lombok.Getter;


// PURPOSE: This request is for the freelancer to progress the state of a milestone. The first
// time this request is called, the internal state of a milestone is moved from OPEN to IN_PROGRESS
// afterward if it is called in the IN_PROGRESS state it is then move to the IN_REVIEW state. Calling this
// endpoint when the milestone is in any other state has no effect.
@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder =  ProgressMilestoneRequest.class)
public class ProgressMilestoneRequest extends CommandRequest {
    private final String freelancerId;
    private final String milestoneId;

}
