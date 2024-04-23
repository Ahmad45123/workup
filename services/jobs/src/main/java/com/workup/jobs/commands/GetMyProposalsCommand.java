package com.workup.jobs.commands;

import com.workup.jobs.models.Attachment;
import com.workup.jobs.models.Milestone;
import com.workup.jobs.models.Proposal;
import com.workup.shared.commands.jobs.proposals.ProposalAttachment;
import com.workup.shared.commands.jobs.proposals.ProposalMilestone;
import com.workup.shared.commands.jobs.proposals.ProposalModel;
import com.workup.shared.commands.jobs.proposals.requests.GetMyProposalsRequest;
import com.workup.shared.commands.jobs.proposals.responses.GetMyProposalsResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.ArrayList;
import java.util.List;

public class GetMyProposalsCommand
  extends JobCommand<GetMyProposalsRequest, GetMyProposalsResponse> {

  @Override
  public GetMyProposalsResponse Run(GetMyProposalsRequest request) {
    try {
      List<Proposal> response = proposalRepository.findByFreelancerId(
        request.getUserId()
      );

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
      return GetMyProposalsResponse
        .builder()
        .withProposals(proposals)
        .withStatusCode(HttpStatusCode.OK)
        .build();
    } catch (Exception e) {
      return GetMyProposalsResponse
        .builder()
        .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
        .withErrorMessage("An error occurred while fetching proposals")
        .build();
    }
  }
}
