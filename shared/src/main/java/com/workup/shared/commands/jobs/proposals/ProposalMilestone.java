package com.workup.shared.commands.jobs.proposals;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = ProposalMilestone.ProposalMilestoneBuilder.class)
public class ProposalMilestone {
    private final String description;
    private final double amount;
    private final Date dueDate;
}
