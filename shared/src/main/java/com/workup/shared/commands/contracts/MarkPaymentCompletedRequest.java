package com.workup.shared.commands.contracts;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.workup.shared.commands.CommandRequest;

public class MarkPaymentCompletedRequest extends CommandRequest {
    public String milestoneId;

    @JsonCreator
    public MarkPaymentCompletedRequest(@JsonProperty("milestoneId") String milestoneId) {
        this.milestoneId = milestoneId;
    }

}
