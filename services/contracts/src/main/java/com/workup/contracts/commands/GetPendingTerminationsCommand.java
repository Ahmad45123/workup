package com.workup.contracts.commands;

import com.workup.contracts.logger.ContractsLogger;
import com.workup.contracts.logger.LoggingLevel;
import com.workup.contracts.models.Contract;
import com.workup.shared.commands.contracts.TerminationRequest;
import com.workup.shared.commands.contracts.requests.GetPendingTerminationsRequest;
import com.workup.shared.commands.contracts.responses.GetPendingTerminationsResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.contracts.TerminationRequestStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GetPendingTerminationsCommand
    extends ContractCommand<GetPendingTerminationsRequest, GetPendingTerminationsResponse> {

  @Override
  public GetPendingTerminationsResponse Run(GetPendingTerminationsRequest request) {

    try {
      String cachingKey = request.getContractId() + "/pending_terminations";
      GetPendingTerminationsResponse cachedResponse =
          (GetPendingTerminationsResponse)
              redisService.getValue(cachingKey, GetPendingTerminationsResponse.class);
      if (cachedResponse != null) {
        ContractsLogger.print(
            "[x] Contract terminations response fetched from cache: " + cachedResponse.toString(),
            LoggingLevel.TRACE);
        return cachedResponse;
      }

      Optional<Contract> contract =
          contractRepository.findById(UUID.fromString(request.getContractId()));
      if (contract.isEmpty())
        return GetPendingTerminationsResponse.builder()
            .withStatusCode(HttpStatusCode.BAD_REQUEST)
            .withErrorMessage("Invalid Contract Id")
            .build();

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
                              .withStatus(req.getStatus())
                              .build())
                  .toList();

      GetPendingTerminationsResponse response =
          GetPendingTerminationsResponse.builder()
              .withTerminationRequests(terminationsList)
              .withStatusCode(HttpStatusCode.OK)
              .withErrorMessage("")
              .build();

      redisService.setValue(cachingKey, response);
      return response;

    } catch (Exception e) {
      ContractsLogger.print(e.getMessage(), LoggingLevel.TRACE);

      return GetPendingTerminationsResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage("Error occurred while fetching pending termination requests")
          .build();
    }
  }
}
