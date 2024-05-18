package com.workup.contracts.commands;

import com.workup.contracts.logger.ContractsLogger;
import com.workup.contracts.models.Contract;
import com.workup.contracts.models.ContractMilestone;
import com.workup.shared.commands.contracts.requests.EvaluateMilestoneRequest;
import com.workup.shared.commands.contracts.responses.EvaluateMilestoneResponse;
import com.workup.shared.commands.payments.paymentrequest.requests.CreatePaymentRequestRequest;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.shared.enums.contracts.MilestoneState;
import java.util.Optional;
import java.util.UUID;

public class EvaluateMilestoneCommand
    extends ContractCommand<EvaluateMilestoneRequest, EvaluateMilestoneResponse> {
  private EvaluateMilestoneResponse isValid(Optional<ContractMilestone> milestoneOptional) {
    if (milestoneOptional.isEmpty())
      return EvaluateMilestoneResponse.builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withErrorMessage("Milestone is not found")
          .build();
    if (milestoneOptional.get().getStatus() != MilestoneState.IN_REVIEW)
      return EvaluateMilestoneResponse.builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withErrorMessage("Milestone cannot be evaluated as it has not progressed enough")
          .build();
    return null;
  }

  @Override
  public EvaluateMilestoneResponse Run(EvaluateMilestoneRequest request) {
    Optional<ContractMilestone> milestoneOptional =
        contractMilestoneRepository.findById(UUID.fromString(request.getMilestoneId()));

    EvaluateMilestoneResponse checkerResponse = isValid(milestoneOptional);
    if (checkerResponse != null) return checkerResponse;

    ContractMilestone updatedMilestone = milestoneOptional.get();

    updatedMilestone.setStatus(request.getEvaluatedState());

    try {
      contractMilestoneRepository.save(updatedMilestone);
      ContractsLogger.print(" [x] Milestone evaluated " + updatedMilestone);

      if (request.getEvaluatedState() == MilestoneState.ACCEPTED) {
        ContractsLogger.print(" [x] Sending payment request ");
        // Getting the contract as we need to send the freelancer and client id since they are
        // in the payment request parameters.
        Optional<Contract> contractOptional =
            contractRepository.findById(UUID.fromString(updatedMilestone.getContractId()));
        if (contractOptional.isEmpty())
          throw new Exception("Contract Optional was empty, therefore unable to fetch data");

        Contract milestoneContract = contractOptional.get();

        CreatePaymentRequestRequest externalRequest =
            CreatePaymentRequestRequest.builder()
                .withAmount(updatedMilestone.getAmount())
                .withClientId(milestoneContract.getClientId())
                .withFreelancerId(milestoneContract.getFreelancerId())
                .withReferenceId(updatedMilestone.getMilestoneId().toString())
                .build();
        rabbitTemplate.convertSendAndReceive(ServiceQueueNames.PAYMENTS, externalRequest);
        ContractsLogger.print(" [x] Payment request sent ");
      }

      return EvaluateMilestoneResponse.builder()
          .withStatusCode(HttpStatusCode.OK)
          .withErrorMessage("")
          .build();

    } catch (Exception e) {
      e.printStackTrace();
      return EvaluateMilestoneResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage(e.getMessage())
          .build();
    }
  }
}
