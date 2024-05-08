package com.workup.contracts.commands;

import com.workup.shared.commands.contracts.requests.EvaluateMilestoneRequest;

import com.workup.shared.commands.contracts.requests.GetContractRequest;
import com.workup.shared.commands.contracts.requests.GetMilestoneRequest;
import com.workup.shared.commands.contracts.requests.GetPendingTerminationsRequest;
import com.workup.shared.commands.contracts.responses.EvaluateMilestoneResponse;
import com.workup.shared.commands.contracts.responses.GetContractResponse;
import com.workup.shared.commands.contracts.responses.GetMilestoneResponse;
import com.workup.shared.commands.contracts.responses.GetPendingTerminationsResponse;

public class GetPendingTerminationsCommand
        extends ContractCommand<GetPendingTerminationsRequest, GetPendingTerminationsResponse> {

    @Override
    public GetPendingTerminationsResponse Run(GetPendingTerminationsRequest request) {
        // First we will get the milestones and add them to the database first,
        // This will allow us to have their IDs for when we insert the contract.

        return null;

    }
}
