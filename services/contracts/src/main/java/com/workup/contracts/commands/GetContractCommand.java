package com.workup.contracts.commands;

import com.workup.shared.commands.contracts.requests.GetContractRequest;
import com.workup.shared.commands.contracts.responses.GetContractResponse;

public class GetContractCommand extends ContractCommand<GetContractRequest, GetContractResponse> {

  @Override
  public GetContractResponse Run(GetContractRequest request) {
    // First we will get the milestones and add them to the database first,
    // This will allow us to have their IDs for when we insert the contract.

    return null;
  }
}
