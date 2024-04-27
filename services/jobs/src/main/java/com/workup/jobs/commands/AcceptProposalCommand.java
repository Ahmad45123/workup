package com.workup.jobs.commands;

import com.workup.jobs.models.Job;
import com.workup.jobs.models.Proposal;
import com.workup.jobs.models.Proposal.ProposalPrimaryKey;
import com.workup.shared.commands.contracts.Milestone;
import com.workup.shared.commands.contracts.requests.InitiateContractRequest;
import com.workup.shared.commands.contracts.responses.InitiateContractResponse;
import com.workup.shared.commands.jobs.proposals.AcceptedJobInfo;
import com.workup.shared.commands.jobs.proposals.AcceptedProposalInfo;
import com.workup.shared.commands.jobs.proposals.ProposalStatus;
import com.workup.shared.commands.jobs.proposals.requests.AcceptProposalRequest;
import com.workup.shared.commands.jobs.proposals.responses.AcceptProposalResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
      if (proposals.isEmpty()) {
        return AcceptProposalResponse
          .builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Proposal not found")
          .build();
      }
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
      if (job.isEmpty()) {
        return AcceptProposalResponse
          .builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Job not found")
          .build();
      }
      Job acceptedJob = job.get();
      if (!acceptedJob.isActive()) {
        return AcceptProposalResponse
          .builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withErrorMessage("This job is no longer active!")
          .build();
      }
      if (!acceptedJob.getClientId().equals(request.getUserId())) {
        return AcceptProposalResponse
          .builder()
          .withStatusCode(HttpStatusCode.UNAUTHORIZED)
          .withErrorMessage("User is not the owner of this proposal's job!")
          .build();
      }
      acceptedJob.setActive(false);
      proposalRepository.save(acceptedProposal);
      jobRepository.save(acceptedJob);
      String contractId = initiateContract(acceptedProposal, acceptedJob, request);
      return AcceptProposalResponse
        .builder()
        .withStatusCode(HttpStatusCode.OK)
        .withJob(
          AcceptedJobInfo.builder().withId(request.getJobId()).withIsActive(false).build()
        )
        .withProposal(
          AcceptedProposalInfo
            .builder()
            .withId(request.getProposalId())
            .withStatus(ProposalStatus.ACCEPTED)
            .build()
        )
        .withMessage("Proposal Accepted Successfully!")
        .withContractId(contractId)
        .build();
    } catch (Exception e) {
      e.printStackTrace();
      return AcceptProposalResponse
        .builder()
        .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
        .withErrorMessage("An error occurred while accepting job proposal")
        .build();
    }
  }

  private String initiateContract(
    Proposal acceptedProposal,
    Job acceptedJob,
    AcceptProposalRequest request
  ) {
    return (
      (InitiateContractResponse) rabbitTemplate.convertSendAndReceive(
        "contractsqueue",
        InitiateContractRequest
          .builder()
          .withFreelancerId(acceptedProposal.getFreelancerId())
          .withJobId(request.getJobId())
          .withProposalId(request.getProposalId())
          .withJobTitle(acceptedJob.getTitle())
          .withClientId(acceptedJob.getClientId())
          .withUserId(request.getUserId())
          .withJobMilestones(
            acceptedProposal
              .getMilestones()
              .stream()
              .map(milestone -> {
                return Milestone
                  .builder()
                  .withAmount(milestone.getAmount())
                  .withDescription(milestone.getDescription())
                  .withDueDate(milestone.getDueDate())
                  .build();
              })
              .collect(Collectors.toList())
          )
          .build()
      )
    ).getContractId();
  }
}
