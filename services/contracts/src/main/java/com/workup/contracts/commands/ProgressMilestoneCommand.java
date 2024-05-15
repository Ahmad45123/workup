package com.workup.contracts.commands;

import com.workup.contracts.logger.ContractsLogger;
import com.workup.contracts.logger.LoggingLevel;
import com.workup.contracts.models.ContractMilestone;
import com.workup.shared.commands.contracts.requests.ProgressMilestoneRequest;
import com.workup.shared.commands.contracts.responses.ProgressMilestoneResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.contracts.MilestoneState;
import java.util.Optional;
import java.util.UUID;

public class ProgressMilestoneCommand
    extends ContractCommand<ProgressMilestoneRequest, ProgressMilestoneResponse> {

  private ProgressMilestoneResponse isValid(Optional<ContractMilestone> milestoneOptional) {
    if (milestoneOptional.isEmpty())
      return ProgressMilestoneResponse.builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withErrorMessage("Milestone is not found")
          .build();

    MilestoneState milestoneState = milestoneOptional.get().getStatus();
    if (milestoneState != MilestoneState.OPEN && milestoneState != MilestoneState.IN_PROGRESS)
      return ProgressMilestoneResponse.builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withErrorMessage("Milestone cannot be progressed through this command")
          .build();
    return null;
  }

  @Override
  public ProgressMilestoneResponse Run(ProgressMilestoneRequest request) {
    Optional<ContractMilestone> milestoneOptional =
        contractMilestoneRepository.findById(UUID.fromString(request.getMilestoneId()));

    ProgressMilestoneResponse checkerResponse = isValid(milestoneOptional);
    if (checkerResponse != null) return checkerResponse;

    ContractMilestone updatedMilestone = milestoneOptional.get();

    progress(updatedMilestone);

    try {
      contractMilestoneRepository.save(updatedMilestone);
      ContractsLogger.print(" [x] Milestone Progressed " + updatedMilestone, LoggingLevel.TRACE);
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

  private void progress(ContractMilestone milestone) {
    if (milestone.getStatus() == MilestoneState.OPEN)
      milestone.setStatus(MilestoneState.IN_PROGRESS);
    else if (milestone.getStatus() == MilestoneState.IN_PROGRESS)
      milestone.setStatus(MilestoneState.IN_REVIEW);
  }
}
