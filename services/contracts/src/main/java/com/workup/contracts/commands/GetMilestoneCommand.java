package com.workup.contracts.commands;

import com.workup.shared.commands.contracts.requests.EvaluateMilestoneRequest;

import com.workup.shared.commands.contracts.requests.GetContractRequest;
import com.workup.shared.commands.contracts.requests.GetMilestoneRequest;
import com.workup.shared.commands.contracts.responses.EvaluateMilestoneResponse;
import com.workup.shared.commands.contracts.responses.GetContractResponse;
import com.workup.shared.commands.contracts.responses.GetMilestoneResponse;

public class GetMilestoneCommand
        extends ContractCommand<GetMilestoneRequest, GetMilestoneResponse> {

    @Override
    public GetMilestoneResponse Run(GetMilestoneRequest request) {
        // First we will get the milestones and add them to the database first,
        // This will allow us to have their IDs for when we insert the contract.

        return null;

    }
}
