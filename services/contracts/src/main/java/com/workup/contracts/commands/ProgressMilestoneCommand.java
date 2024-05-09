package com.workup.contracts.commands;

import com.workup.contracts.models.ContractMilestone;
import com.workup.shared.commands.contracts.requests.ProgressMilestoneRequest;
import com.workup.shared.commands.contracts.responses.ProgressMilestoneResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.contracts.MilestoneState;
import java.util.Optional;
import java.util.UUID;

public class ProgressMilestoneCommand
    extends ContractCommand<ProgressMilestoneRequest, ProgressMilestoneResponse> {

  private ProgressMilestoneResponse isValid(ProgressMilestoneRequest request) {
    Optional<ContractMilestone> milestone =
        contractMilestoneRepository.findById(UUID.fromString(request.getMilestoneId()));
    if (milestone.isEmpty())
      return ProgressMilestoneResponse.builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withErrorMessage("Milestone is not found")
          .build();
    if (milestone.get().getStatus() != MilestoneState.OPEN
        && milestone.get().getStatus() != MilestoneState.IN_PROGRESS)
      return ProgressMilestoneResponse.builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withErrorMessage("Milestone cannot be progressed through this command")
          .build();
    return null;
  }

  @Override
  public ProgressMilestoneResponse Run(ProgressMilestoneRequest request) {
    ProgressMilestoneResponse checkerResponse = isValid(request);
    if (checkerResponse != null) return checkerResponse;

    Optional<ContractMilestone> milestone =
        contractMilestoneRepository.findById(UUID.fromString(request.getMilestoneId()));
    ContractMilestone updatedMilestone = milestone.get();
    if (updatedMilestone.getStatus() == MilestoneState.OPEN) {
      updatedMilestone.setStatus(MilestoneState.IN_PROGRESS);
    } else if (updatedMilestone.getStatus() == MilestoneState.IN_PROGRESS) {
      updatedMilestone.setStatus(MilestoneState.IN_REVIEW);
      // Send to payments here

      // get required data from milestone
      milestoneContract =
          contractRepository.findById(UUID.fromString(updatedMilestone.getContractId()));
    }

    try {
      contractMilestoneRepository.save(updatedMilestone);
      System.out.println(" [x] Milestone Progressed " + updatedMilestone);
      return ProgressMilestoneResponse.builder()
          .withStatusCode(HttpStatusCode.OK)
          .withErrorMessage("")
          .build();

    } catch (Exception e) {
      e.printStackTrace();
      return ProgressMilestoneResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage(e.getMessage())
          .build();
    }
  }
}
