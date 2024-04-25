package com.workup.contracts.commands;

import com.workup.contracts.models.ContractMilestone;
import com.workup.shared.commands.contracts.Milestone;
import com.workup.shared.commands.contracts.requests.ViewContractMilestonesRequest;
import com.workup.shared.commands.contracts.responses.ViewContractMilestonesResponse;
import java.util.List;

public class ViewContractMilestonesCommand
  extends ContractCommand<ViewContractMilestonesRequest, ViewContractMilestonesResponse> {

  @Override
  public ViewContractMilestonesResponse Run(ViewContractMilestonesRequest request) {
    List<ContractMilestone> milestones = contractMilestoneRepository.findByContractId(
      request.getContractId()
    );

    @SuppressWarnings("unchecked")
    List<Milestone> contractMilestones = (List<Milestone>) milestones
      .stream()
      .map(milestone ->
        Milestone
          .builder()
          .withMilestoneId(milestone.getMilestoneId().toString())
          .withContractId(milestone.getContractId())
          .withDescription(milestone.getDescription())
          .withDueDate(milestone.getDueDate())
          .withAmount(milestone.getAmount())
          .build()
      )
      .toList();

    return ViewContractMilestonesResponse
      .builder()
      .withContractMilestones(contractMilestones)
      .build();
  }
}
