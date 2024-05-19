package com.workup.contracts.commands;

import com.workup.contracts.logger.ContractsLogger;
import com.workup.contracts.logger.LoggingLevel;
import com.workup.contracts.models.Contract;
import com.workup.contracts.models.TerminationRequest;
import com.workup.shared.commands.contracts.requests.HandleTerminationRequest;
import com.workup.shared.commands.contracts.responses.HandleTerminationResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.contracts.ContractState;
import com.workup.shared.enums.contracts.TerminationRequestStatus;
import java.util.Optional;
import java.util.UUID;

public class HandleTerminationRequestCommand
    extends ContractCommand<HandleTerminationRequest, HandleTerminationResponse> {

  // request body
  public HandleTerminationResponse Run(HandleTerminationRequest request) {
    Optional<TerminationRequest> terminationRequest =
        terminationRequestRepository.findById(
            UUID.fromString(request.getContractTerminationRequestId()));
    if (terminationRequest.isEmpty()) {
      return HandleTerminationResponse.builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withErrorMessage("No Termination Requests Found")
          .build();
    }

    return handleTerminationRequest(terminationRequest.get(), request.getChosenStatus());
  }

  private HandleTerminationResponse handleTerminationRequest(
      TerminationRequest terminationRequest, TerminationRequestStatus newStatus) {
    terminationRequest.setStatus(newStatus);

    // Terminating the contract
    if (newStatus == TerminationRequestStatus.ACCEPTED) {
      HandleTerminationResponse validatorResponse =
          terminateContract(terminationRequest.getContractId());
      if (validatorResponse != null) return validatorResponse;
    }
    try {
      TerminationRequest updatedRequest = terminationRequestRepository.save(terminationRequest);
      ContractsLogger.print(
          " [x] Updated Termination Request " + updatedRequest, LoggingLevel.TRACE);
      return HandleTerminationResponse.builder()
          .withRequestStatus(updatedRequest.getStatus())
          .withStatusCode(HttpStatusCode.OK)
          .withErrorMessage("")
          .build();
    } catch (Exception e) {
      e.printStackTrace();
      return HandleTerminationResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage(e.getMessage())
          .build();
    }
  }

  private HandleTerminationResponse terminateContract(String contractId) {
    Optional<Contract> contract = contractRepository.findById(UUID.fromString(contractId));
    if (contract.isEmpty() || contract.get().getStatus() != ContractState.ACTIVE)
      return HandleTerminationResponse.builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withErrorMessage("The contract has already ended.")
          .build();
    Contract updatedContract = contract.get();
    updatedContract.setStatus(ContractState.TERMINATED);
    try {
      updatedContract = contractRepository.save(updatedContract);
      ContractsLogger.print(
          " [x] Updated Contract Status to Terminated " + updatedContract, LoggingLevel.TRACE);
    } catch (Exception e) {
      e.printStackTrace();
      return HandleTerminationResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage(e.getMessage())
          .build();
    }
    return null;
  }
}
