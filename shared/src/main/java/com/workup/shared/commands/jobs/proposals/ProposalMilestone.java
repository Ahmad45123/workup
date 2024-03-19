package com.workup.shared.commands.jobs.proposals;

import java.util.Date;

public record ProposalMilestone(String description, double amount, Date dueDate) {
}
