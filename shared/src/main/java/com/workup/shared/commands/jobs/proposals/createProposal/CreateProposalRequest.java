package com.workup.shared.commands.jobs.proposals.createProposal;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.jobs.proposals.JobDuration;
import com.workup.shared.commands.jobs.proposals.ProposalAttachment;
import com.workup.shared.commands.jobs.proposals.ProposalMilestone;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = CreateProposalRequest.CreateProposalRequestBuilder.class)
public class CreateProposalRequest extends CommandRequest {
    private final String freelancerId;
    private final String coverLetter;
    private final String jobId;
    private final JobDuration jobDuration;
    private final ArrayList<ProposalAttachment> attachments;
    private final ArrayList<ProposalMilestone> milestones;
}
