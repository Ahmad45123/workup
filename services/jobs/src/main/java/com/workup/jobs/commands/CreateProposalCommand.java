package com.workup.jobs.commands;

import com.workup.jobs.models.Attachment;
import com.workup.jobs.models.Job;
import com.workup.jobs.models.Milestone;
import com.workup.jobs.models.Proposal;
import com.workup.shared.commands.jobs.proposals.ProposalStatus;
import com.workup.shared.commands.jobs.proposals.requests.CreateProposalRequest;
import com.workup.shared.commands.jobs.proposals.responses.CreateProposalResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateProposalCommand
    extends JobCommand<CreateProposalRequest, CreateProposalResponse> {
  private static final Logger logger = LogManager.getLogger(CreateProposalCommand.class);

  @Override
  public CreateProposalResponse Run(CreateProposalRequest request) {
    logger.info("[x] Saving proposal for job '" + request.getJobId() + "'");
    try {
      Optional<Job> job = jobRepository.findById(UUID.fromString(request.getJobId()));
      if (!job.isPresent()) {
        return CreateProposalResponse.builder()
            .withStatusCode(HttpStatusCode.NOT_FOUND)
            .withErrorMessage("Job Not Found")
            .withId(null)
            .build();
      }
      Proposal proposal =
          Proposal.builder()
              .withPrimaryKey(
                  Proposal.ProposalPrimaryKey.builder()
                      .withJobId(request.getJobId())
                      .withId(UUID.randomUUID())
                      .build())
              .withFreelancerId(request.getUserId())
              .withCoverLetter(request.getCoverLetter())
              .withDuration(request.getJobDuration())
              .withAttachments(
                  request.getAttachments() != null
                      ? request.getAttachments().stream()
                          .map(
                              attachment ->
                                  Attachment.builder()
                                      .withName(attachment.getName())
                                      .withUrl(attachment.getUrl())
                                      .build())
                          .collect(Collectors.toCollection(ArrayList::new))
                      : null)
              .withMilestones(
                  request.getMilestones() != null
                      ? request.getMilestones().stream()
                          .map(
                              milestone ->
                                  Milestone.builder()
                                      .withAmount(milestone.getAmount())
                                      .withDescription(milestone.getDescription())
                                      .withDueDate(milestone.getDueDate())
                                      .build())
                          .collect(Collectors.toCollection(ArrayList::new))
                      : null)
              .withCreatedAt(new Date())
              .withUpdatedAt(new Date())
              .withStatus(ProposalStatus.PENDING)
              .build();
      Proposal savedProposal = proposalRepository.save(proposal);
      logger.info("[x] Proposal saved with id: " + savedProposal.getPrimaryKey().getId());
      return CreateProposalResponse.builder()
          .withStatusCode(HttpStatusCode.CREATED)
          .withId(savedProposal.getPrimaryKey().getId().toString())
          .build();
    } catch (Exception e) {
      logger.error("[x] An error occurred while saving proposal", e.getMessage());
      return CreateProposalResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage("An error occurred while saving proposal")
          .withId(null)
          .build();
    }
  }
}
