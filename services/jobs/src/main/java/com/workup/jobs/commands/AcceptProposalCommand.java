package com.workup.jobs.commands;

import com.workup.jobs.models.Job;
import com.workup.jobs.models.Proposal;
import com.workup.jobs.models.Proposal.ProposalPrimaryKey;
import com.workup.shared.commands.jobs.proposals.AcceptedJobInfo;
import com.workup.shared.commands.jobs.proposals.AcceptedProposalInfo;
import com.workup.shared.commands.jobs.proposals.ProposalStatus;
import com.workup.shared.commands.jobs.proposals.requests.AcceptProposalRequest;
import com.workup.shared.commands.jobs.proposals.responses.AcceptProposalResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.Optional;
import java.util.UUID;

public class AcceptProposalCommand
  extends JobCommand<AcceptProposalRequest, AcceptProposalResponse> {

  @Override
  public AcceptProposalResponse Run(AcceptProposalRequest request) {
    try {
      UUID proposalId = UUID.fromString(request.getProposalId());
      ProposalPrimaryKey proposalPrimaryKey = ProposalPrimaryKey
        .builder()
        .withId(proposalId)
        .withJobId(request.getJobId())
        .build();
      Optional<Proposal> proposals = proposalRepository.findById(proposalPrimaryKey);
      if (proposals.isPresent()) {
        Proposal acceptedProposal = proposals.get();
        if (acceptedProposal.getStatus() != ProposalStatus.PENDING) {
          return AcceptProposalResponse
            .builder()
            .withStatusCode(HttpStatusCode.BAD_REQUEST)
            .withErrorMessage("Proposal is not pending")
            .build();
        }
        acceptedProposal.setStatus(ProposalStatus.ACCEPTED);

        UUID jobId = UUID.fromString(request.getJobId());
        Optional<Job> job = jobRepository.findById(jobId);
        if (job.isPresent()) {
          Job acceptedJob = job.get();
          if (!acceptedJob.isActive()) {
            return AcceptProposalResponse
              .builder()
              .withStatusCode(HttpStatusCode.BAD_REQUEST)
              .withErrorMessage("This job is no longer active!")
              .build();
          }
          acceptedJob.setActive(false);
          proposalRepository.save(acceptedProposal);
          jobRepository.save(acceptedJob);
          return AcceptProposalResponse
            .builder()
            .withStatusCode(HttpStatusCode.OK)
            .withJob(
              AcceptedJobInfo
                .builder()
                .withId(request.getJobId())
                .withIsActive(false)
                .build()
            )
            .withProposal(
              AcceptedProposalInfo
                .builder()
                .withId(request.getProposalId())
                .withStatus(ProposalStatus.ACCEPTED)
                .build()
            )
            .withMessage("Proposal Accepted Successfully!")
            .build();
        } else {
          return AcceptProposalResponse
            .builder()
            .withStatusCode(HttpStatusCode.NOT_FOUND)
            .withErrorMessage("Job not found")
            .build();
        }
      } else {
        return AcceptProposalResponse
          .builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage("Proposal not found")
          .build();
      }
    } catch (Exception e) {
      e.printStackTrace();
      return AcceptProposalResponse
        .builder()
        .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
        .withErrorMessage("An error occurred while accepting job proposal")
        .withJob(
          AcceptedJobInfo.builder().withId(request.getJobId()).withIsActive(true).build()
        )
        .withProposal(
          AcceptedProposalInfo
            .builder()
            .withId(request.getProposalId())
            .withStatus(ProposalStatus.PENDING)
            .build()
        )
        .build();
    }
  }
}
