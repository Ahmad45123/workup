package com.workup.shared.commands.jobs.proposals.requests;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.jobs.proposals.JobDuration;
import com.workup.shared.commands.jobs.proposals.ProposalAttachment;
import com.workup.shared.commands.jobs.proposals.ProposalMilestone;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@JsonSerialize
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class CreateProposalRequest extends CommandRequest {

  private final String coverLetter;
  private final String jobId;
  private final JobDuration jobDuration;
  private final List<ProposalAttachment> attachments;
  private final List<ProposalMilestone> milestones;
}
