package com.workup.contracts.commands;

import com.workup.contracts.logger.ContractsLogger;
import com.workup.contracts.logger.LoggingLevel;
import com.workup.contracts.models.Contract;
import com.workup.shared.commands.contracts.requests.GetContractRequest;
import com.workup.shared.commands.contracts.responses.GetContractResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.Optional;
import java.util.UUID;

public class GetContractCommand extends ContractCommand<GetContractRequest, GetContractResponse> {

  @Override
  public GetContractResponse Run(GetContractRequest request) {
    try {
      Optional<Contract> contractOptional =
          contractRepository.findById(UUID.fromString(request.getContractId()));

      if (contractOptional.isEmpty()) {
        return GetContractResponse.builder()
            .withStatusCode(HttpStatusCode.NOT_FOUND)
            .withErrorMessage("Requested contract does not exist")
            .build();
      }

      Contract contract = contractOptional.get();

      GetContractResponse response =
          GetContractResponse.builder()
              .withContractId(contract.getContractId().toString())
              .withProposalId(contract.getProposalId())
              .withJobId(contract.getJobId())
              .withJobTitle(contract.getJobTitle())
              .withClientId(contract.getClientId())
              .withFreelancerId(contract.getFreelancerId())
              .withMilestonesIds(contract.getMilestonesIds())
              .withStatus(contract.getStatus())
              .withStatusCode(HttpStatusCode.OK)
              .withErrorMessage("")
              .build();

      return response;
    } catch (Exception ex) {
      ContractsLogger.print(
          "[x] Error occurred while fetching contract: " + ex.getMessage(), LoggingLevel.TRACE);
      ex.printStackTrace();
      return GetContractResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage("Error occurred while fetching contract")
          .build();
    }
  }
}
