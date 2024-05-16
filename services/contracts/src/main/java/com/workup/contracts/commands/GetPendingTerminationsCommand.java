package com.workup.contracts.commands;

import com.workup.contracts.logger.ContractsLogger;
import com.workup.contracts.logger.LoggingLevel;
import com.workup.shared.commands.contracts.TerminationRequest;
import com.workup.shared.commands.contracts.requests.GetPendingTerminationsRequest;
import com.workup.shared.commands.contracts.responses.GetPendingTerminationsResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.contracts.TerminationRequestStatus;
import java.util.List;

public class GetPendingTerminationsCommand
    extends ContractCommand<GetPendingTerminationsRequest, GetPendingTerminationsResponse> {

  @Override
  public GetPendingTerminationsResponse Run(GetPendingTerminationsRequest request) {

    try {
      @SuppressWarnings("unchecked")
      List<TerminationRequest> terminationsList =
          (List<TerminationRequest>)
              terminationRequestRepository
                  .findByContractIdAndStatus(
                      request.getContractId(), TerminationRequestStatus.PENDING)
                  .stream()
                  .map(
                      req ->
                          TerminationRequest.builder()
                              .withRequesterId(req.getRequesterId())
                              .withRequestId(String.valueOf(req.getRequestId()))
                              .withContractId(req.getContractId())
                              .withReason(req.getReason())
                              .build());

      if (terminationsList.isEmpty()) {
        return GetPendingTerminationsResponse.builder()
            .withStatusCode(HttpStatusCode.NOT_FOUND)
            .withErrorMessage("No pending terminations exist")
            .build();
      }

      return GetPendingTerminationsResponse.builder()
          .withTerminationRequests(terminationsList)
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
