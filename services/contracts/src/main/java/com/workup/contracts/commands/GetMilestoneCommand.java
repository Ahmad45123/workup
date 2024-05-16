package com.workup.contracts.commands;

import com.workup.contracts.models.ContractMilestone;
import com.workup.shared.commands.contracts.requests.GetMilestoneRequest;
import com.workup.shared.commands.contracts.responses.GetMilestoneResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.Optional;
import java.util.UUID;

public class GetMilestoneCommand
    extends ContractCommand<GetMilestoneRequest, GetMilestoneResponse> {

  @Override
  public GetMilestoneResponse Run(GetMilestoneRequest request) {

    Optional<ContractMilestone> milestoneOptional =
        contractMilestoneRepository.findById(UUID.fromString(request.getMilestoneId()));

    if (milestoneOptional.isEmpty()) {
      return GetMilestoneResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Requested milestone  does not exist")
          .build();
    }

    ContractMilestone milestone = milestoneOptional.get();

    return GetMilestoneResponse.builder()
        .withContractId(milestone.getContractId())
        .withMilestoneId(milestone.getMilestoneId().toString())
        .withAmount(milestone.getAmount())
        .withDescription(milestone.getDescription())
        .withDueDate(milestone.getDueDate())
        .withStatus(milestone.getStatus())
        .withAmount(milestone.getAmount())
        .withStatusCode(HttpStatusCode.OK)
        .withErrorMessage("")
        .build();
  }
}
