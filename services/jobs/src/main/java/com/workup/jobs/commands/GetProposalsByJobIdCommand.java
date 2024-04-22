package com.workup.jobs.commands;

import com.workup.jobs.models.Attachment;
import com.workup.jobs.models.Milestone;
import com.workup.jobs.models.Proposal;
import com.workup.shared.commands.jobs.proposals.ProposalAttachment;
import com.workup.shared.commands.jobs.proposals.ProposalMilestone;
import com.workup.shared.commands.jobs.proposals.ProposalModel;
import com.workup.shared.commands.jobs.proposals.requests.GetProposalsByJobIdRequest;
import com.workup.shared.commands.jobs.proposals.responses.GetProposalsByJobIdResponse;
import java.util.ArrayList;
import java.util.List;

public class GetProposalsByJobIdCommand
  extends JobCommand<GetProposalsByJobIdRequest, GetProposalsByJobIdResponse> {

  @Override
  public GetProposalsByJobIdResponse Run(GetProposalsByJobIdRequest request) {
    List<Proposal> response = proposalRepository.findByJobId(request.getJobId());

    List<ProposalModel> proposals = new ArrayList<>();
    for (Proposal proposal : response) {
      ArrayList<ProposalAttachment> attachments = new ArrayList<>();
      for (Attachment attachment : proposal.getAttachments()) {
        attachments.add(
          ProposalAttachment
            .builder()
            .withName(attachment.getName())
            .withName(attachment.getUrl())
            .build()
        );
      }
      ArrayList<ProposalMilestone> milestones = new ArrayList<>();
      for (Milestone milestone : proposal.getMilestones()) {
        milestones.add(
          ProposalMilestone
            .builder()
            .withAmount(milestone.getAmount())
            .withDescription(milestone.getDescription())
            .withDueDate(milestone.getDueDate())
            .build()
        );
      }
      proposals.add(
        ProposalModel
          .builder()
          .withId(proposal.getPrimaryKey().getId().toString())
          .withJobId(proposal.getPrimaryKey().getJobId())
          .withFreelancerId(proposal.getFreelancerId())
          .withCoverLetter(proposal.getCoverLetter())
          .withStatus(proposal.getStatus())
          .withCreatedAt(proposal.getCreatedAt())
          .withModifiedAt(proposal.getUpdatedAt())
          .withDuration(proposal.getDuration())
          .withAttachments(attachments)
          .withMilestones(milestones)
          .build()
      );
    }
    return GetProposalsByJobIdResponse.builder().withProposals(proposals).build();
  }
}
