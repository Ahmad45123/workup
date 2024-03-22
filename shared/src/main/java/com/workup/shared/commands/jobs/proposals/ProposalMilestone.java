package com.workup.shared.commands.jobs.proposals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record ProposalMilestone(String description, double amount, Date dueDate) {
    @JsonCreator
    public ProposalMilestone(@JsonProperty("description") String description,
                             @JsonProperty("amount") double amount,
                             @JsonProperty("dueDate") Date dueDate) {
        this.description = description;
        this.amount = amount;
        this.dueDate = dueDate;
    }
}
