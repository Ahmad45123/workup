package com.workup.shared.commands.jobs.proposals;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@JsonSerialize
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = ProposalMilestone.ProposalMilestoneBuilder.class)
public class ProposalMilestone {
    private final String description;
    private final double amount;
    private final Date dueDate;
}
