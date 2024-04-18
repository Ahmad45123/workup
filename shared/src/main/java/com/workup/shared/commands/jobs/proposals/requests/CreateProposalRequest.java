package com.workup.shared.commands.jobs.proposals.requests;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.jobs.proposals.JobDuration;
import com.workup.shared.commands.jobs.proposals.ProposalAttachment;
import com.workup.shared.commands.jobs.proposals.ProposalMilestone;
import lombok.experimental.SuperBuilder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@JsonSerialize
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class CreateProposalRequest extends CommandRequest {
    private final String freelancerId;
    private final String coverLetter;
    private final String jobId;
    private final JobDuration jobDuration;
    private final ArrayList<ProposalAttachment> attachments;
    private final ArrayList<ProposalMilestone> milestones;
}
