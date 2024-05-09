package com.workup.contracts.commands;

import com.workup.contracts.logger.ContractsLogger;
import com.workup.contracts.models.Contract;
import com.workup.shared.commands.contracts.requests.GetContractRequest;
import com.workup.shared.commands.contracts.responses.GetContractResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.redis.RedisService;

import java.util.Optional;
import java.util.UUID;

public class GetContractCommand extends ContractCommand<GetContractRequest, GetContractResponse> {

  @Override
  public GetContractResponse Run(GetContractRequest request) {
    String cachingKey = request.getContractId();
    GetContractResponse cachedResponse =
            (GetContractResponse)
                    redisService.getValue(cachingKey, GetContractResponse.class);
    if (cachedResponse != null) {
      ContractsLogger.print("[x] Contract request response fetched from cache: " + cachedResponse.toString());

      return cachedResponse;
    }


    Optional<Contract> contractOptional = contractRepository.findById(UUID.fromString(request.getContractId()));

    if(contractOptional.isEmpty()){
      return GetContractResponse.builder()
              .withStatusCode(HttpStatusCode.NOT_FOUND)
              .withErrorMessage("Requested contract does not exist")
              .build();
    }

    Contract contract = contractOptional.get();

    GetContractResponse response = GetContractResponse
            .builder()
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

    redisService.setValue(cachingKey, response);
    return response;
  }
}
