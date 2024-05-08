package com.workup.contracts.commands;


import com.workup.shared.commands.contracts.requests.ProgressMilestoneRequest;
import com.workup.shared.commands.contracts.responses.ProgressMilestoneResponse;

public class ProgressMilestoneCommand
    extends ContractCommand<ProgressMilestoneRequest, ProgressMilestoneResponse> {

  @Override
  public ProgressMilestoneResponse Run(ProgressMilestoneRequest request) {
    // First we will get the milestones and add them to the database first,
    // This will allow us to have their IDs for when we insert the contract.

    return null;
  }
}
