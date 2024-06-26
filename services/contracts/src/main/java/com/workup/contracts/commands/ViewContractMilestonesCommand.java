package com.workup.contracts.commands;

import com.workup.contracts.models.Contract;
import com.workup.contracts.models.ContractMilestone;
import com.workup.shared.commands.contracts.Milestone;
import com.workup.shared.commands.contracts.requests.ViewContractMilestonesRequest;
import com.workup.shared.commands.contracts.responses.ViewContractMilestonesResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ViewContractMilestonesCommand
    extends ContractCommand<ViewContractMilestonesRequest, ViewContractMilestonesResponse> {

  @Override
  public ViewContractMilestonesResponse Run(ViewContractMilestonesRequest request) {

    Optional<Contract> contract =
        contractRepository.findById(UUID.fromString(request.getContractId()));
    if (contract.isEmpty())
      return ViewContractMilestonesResponse.builder()
          .withErrorMessage("Invalid contract id")
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .build();

    List<ContractMilestone> milestones =
        contractMilestoneRepository.findByContractId(request.getContractId());

    @SuppressWarnings("unchecked")
    List<Milestone> contractMilestones =
        (List<Milestone>)
            milestones.stream()
                .map(
                    milestone ->
                        Milestone.builder()
                            .withMilestoneId(milestone.getMilestoneId().toString())
                            .withContractId(milestone.getContractId())
                            .withDescription(milestone.getDescription())
                            .withDueDate(milestone.getDueDate())
                            .withStatus(milestone.getStatus())
                            .withAmount(milestone.getAmount())
                            .build())
                .toList();

    return ViewContractMilestonesResponse.builder()
        .withContractMilestones(contractMilestones)
        .withStatusCode(HttpStatusCode.OK)
        .build();
  }
}
