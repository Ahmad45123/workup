package com.workup.contracts.commands;

import com.workup.contracts.models.Contract;
import com.workup.contracts.models.TerminationRequest;
import com.workup.shared.commands.contracts.requests.ContractTerminationRequest;
import com.workup.shared.commands.contracts.responses.ContractTerminationResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.contracts.ContractState;
import com.workup.shared.enums.contracts.TerminationRequestStatus;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RequestContractTerminationCommand
  extends ContractCommand<ContractTerminationRequest, ContractTerminationResponse> {

  private ContractTerminationResponse isValidRequest(ContractTerminationRequest request) {
    Optional<Contract> contract = contractRepository.findById(
      UUID.fromString(request.getContractId())
    );
    if (contract.isEmpty()) {
      return ContractTerminationResponse
        .builder()
        .withStatusCode(HttpStatusCode.BAD_REQUEST)
        .withErrorMessage("The contract is not valid.")
        .build();
    }
    if (
      contract.get().getStatus() != ContractState.ACTIVE
    ) return ContractTerminationResponse
      .builder()
      .withStatusCode(HttpStatusCode.BAD_REQUEST)
      .withErrorMessage("The contract is not active anymore.")
      .build();
    //check that requester id is a part of the contract
    String freelancerId = contract.get().getFreelancerId();
    String clientId = contract.get().getClientId();
    String requesterId = request.getUserId();
    if (!freelancerId.equals(requesterId) && !clientId.equals(requesterId)) {
      return ContractTerminationResponse
        .builder()
        .withStatusCode(HttpStatusCode.UNAUTHORIZED)
        .withErrorMessage("Unauthorized request")
        .build();
    }
    return null;
  }

  @Override
  public ContractTerminationResponse Run(ContractTerminationRequest request) {
    ContractTerminationResponse checkerResponse = isValidRequest(request);
    if (checkerResponse != null) return checkerResponse;

    //Check if there is a termination request pending for this user on that contract
    List<TerminationRequest> existedRequests = terminationRequestRepository.findByRequesterIdAndContractIdAndStatus(
      request.getUserId(),
      request.getContractId(),
      TerminationRequestStatus.PENDING
    );
    if (!existedRequests.isEmpty()) return ContractTerminationResponse
      .builder()
      .withStatusCode(HttpStatusCode.BAD_REQUEST)
      .withErrorMessage("Termination Request already exists")
      .build();
    //create the request
    TerminationRequest terminationRequest = TerminationRequest
      .builder()
      .withRequestId(UUID.randomUUID())
      .withContractId(request.getContractId())
      .withRequesterId(request.getUserId())
      .withReason(request.getReason())
      .withStatus(TerminationRequestStatus.PENDING)
      .build();

    try {
      TerminationRequest savedRequest = terminationRequestRepository.save(
        terminationRequest
      );

      System.out.println(" [x] Saved Termination Request '" + savedRequest);

      return ContractTerminationResponse
        .builder()
        .withRequestId(savedRequest.getRequestId().toString())
        .withStatusCode(HttpStatusCode.CREATED)
        .withErrorMessage("")
        .build();
    } catch (Exception e) {
      e.printStackTrace();
      return ContractTerminationResponse
        .builder()
        .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
        .withErrorMessage(e.getMessage())
        .build();
    }
  }
}
