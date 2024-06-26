package com.workup.jobs.commands;

import com.workup.jobs.models.Attachment;
import com.workup.jobs.models.Milestone;
import com.workup.jobs.models.Proposal;
import com.workup.shared.commands.jobs.proposals.ProposalAttachment;
import com.workup.shared.commands.jobs.proposals.ProposalMilestone;
import com.workup.shared.commands.jobs.proposals.ProposalModel;
import com.workup.shared.commands.jobs.proposals.requests.GetProposalsByJobIdRequest;
import com.workup.shared.commands.jobs.proposals.responses.GetProposalsByJobIdResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetProposalsByJobIdCommand
    extends JobCommand<GetProposalsByJobIdRequest, GetProposalsByJobIdResponse> {
  private static final Logger logger = LogManager.getLogger(GetProposalsByJobIdCommand.class);

  @Override
  public GetProposalsByJobIdResponse Run(GetProposalsByJobIdRequest request) {
    logger.info("[x] Fetching proposals for job " + request.getJobId());
    try {
      List<Proposal> response = proposalRepository.findByJobId(request.getJobId());
      logger.info("[x] Found " + response.size() + " proposals for job " + request.getJobId());
      List<ProposalModel> proposals = new ArrayList<>();
      for (Proposal proposal : response) {
        ArrayList<ProposalAttachment> attachments = new ArrayList<>();
        if (proposal.getAttachments() != null) {
          for (Attachment attachment : proposal.getAttachments()) {
            attachments.add(
                ProposalAttachment.builder()
                    .withName(attachment.getName())
                    .withUrl(attachment.getUrl())
                    .build());
          }
        }
        ArrayList<ProposalMilestone> milestones = new ArrayList<>();
        if (proposal.getMilestones() != null) {
          for (Milestone milestone : proposal.getMilestones()) {
            milestones.add(
                ProposalMilestone.builder()
                    .withAmount(milestone.getAmount())
                    .withDescription(milestone.getDescription())
                    .withDueDate(milestone.getDueDate())
                    .build());
          }
        }
        proposals.add(
            ProposalModel.builder()
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
                .build());
      }
      logger.info("[x] Returning " + proposals.size() + " proposals for job " + request.getJobId());
      return GetProposalsByJobIdResponse.builder()
          .withProposals(proposals)
          .withStatusCode(HttpStatusCode.OK)
          .build();
    } catch (Exception e) {
      logger.error("[x] An error occurred while fetching proposals" + e.getMessage());
      return GetProposalsByJobIdResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage("An error occurred while fetching proposals")
          .build();
    }
  }
}
