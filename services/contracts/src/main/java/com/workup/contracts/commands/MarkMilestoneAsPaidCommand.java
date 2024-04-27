package com.workup.contracts.commands;

import com.workup.contracts.models.ContractMilestone;
import com.workup.shared.commands.contracts.requests.MarkPaymentCompletedRequest;
import com.workup.shared.commands.contracts.responses.MarkPaymentCompletedResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.contracts.MilestoneState;
import java.util.Optional;
import java.util.UUID;

public class MarkMilestoneAsPaidCommand
  extends ContractCommand<MarkPaymentCompletedRequest, MarkPaymentCompletedResponse> {

  private MarkPaymentCompletedResponse isValid(MarkPaymentCompletedRequest request) {
    Optional<ContractMilestone> milestone = contractMilestoneRepository.findById(
      UUID.fromString(request.getMilestoneId())
    );
    if (milestone.isEmpty()) return MarkPaymentCompletedResponse
      .builder()
      .withStatusCode(HttpStatusCode.BAD_REQUEST)
      .withErrorMessage("Milestone is not found")
      .build();
    if (
      milestone.get().getStatus() != MilestoneState.ACCEPTED
    ) return MarkPaymentCompletedResponse
      .builder()
      .withStatusCode(HttpStatusCode.BAD_REQUEST)
      .withErrorMessage("Milestone is not accepted for payment")
      .build();
    return null;
  }

  public MarkPaymentCompletedResponse Run(MarkPaymentCompletedRequest request) {
    MarkPaymentCompletedResponse checkerResponse = isValid(request);
    if (checkerResponse != null) return checkerResponse;

    Optional<ContractMilestone> milestone = contractMilestoneRepository.findById(
      UUID.fromString(request.getMilestoneId())
    );
    ContractMilestone updatedMilestone = milestone.get();
    pay(updatedMilestone);

    try {
      contractMilestoneRepository.save(updatedMilestone);
      System.out.println(" [x] Marked Milestone as Paid '" + updatedMilestone);
    } catch (Exception e) {
      e.printStackTrace();
      return MarkPaymentCompletedResponse
        .builder()
        .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
        .withErrorMessage(e.getMessage())
        .build();
    }

    return null;
  }

  private void pay(ContractMilestone milestone)
  {
    milestone.setStatus(MilestoneState.PAID);
  }
}
