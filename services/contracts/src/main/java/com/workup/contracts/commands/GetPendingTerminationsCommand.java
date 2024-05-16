package com.workup.contracts.commands;

import com.workup.contracts.logger.ContractsLogger;
import com.workup.contracts.logger.LoggingLevel;
import com.workup.contracts.models.TerminationRequest;
import com.workup.shared.commands.contracts.requests.GetPendingTerminationsRequest;
import com.workup.shared.commands.contracts.responses.GetPendingTerminationsResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.List;

public class GetPendingTerminationsCommand
    extends ContractCommand<GetPendingTerminationsRequest, GetPendingTerminationsResponse> {

  @Override
  public GetPendingTerminationsResponse Run(GetPendingTerminationsRequest request) {

    try {
      List<TerminationRequest> terminationsList =
          terminationRequestRepository.findByContractId(request.getContractId());

      if (terminationsList.isEmpty()) {
        return GetPendingTerminationsResponse.builder()
            .withStatusCode(HttpStatusCode.NOT_FOUND)
            .withErrorMessage("No pending terminations exist")
            .build();
      }

      TerminationRequest terminationRequest = terminationsList.getFirst();

      return GetPendingTerminationsResponse.builder()
          .withRequestId(terminationRequest.getRequestId().toString())
          .withRequesterId(terminationRequest.getRequesterId())
          .withContractId(terminationRequest.getContractId())
          .withReason(terminationRequest.getReason())
          .withStatus(terminationRequest.getStatus())
          .withStatusCode(HttpStatusCode.OK)
          .withErrorMessage("")
          .build();
    } catch (Exception e) {
      ContractsLogger.print(e.getMessage(), LoggingLevel.TRACE);

      return GetPendingTerminationsResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage("Error occurred while fetching pending termination requests")
          .build();
    }
  }
}
