package com.workup.shared.commands.jobs.proposals;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.experimental.SuperBuilder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@Getter
@JsonSerialize
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class ProposalMilestone {
    private final String description;
    private final double amount;
    private final Date dueDate;
}
