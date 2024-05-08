package com.workup.contracts.commands;

import com.workup.shared.commands.contracts.requests.EvaluateMilestoneRequest;

import com.workup.shared.commands.contracts.responses.EvaluateMilestoneResponse;

public class EvaluateMilestoneCommand
        extends ContractCommand<EvaluateMilestoneRequest, EvaluateMilestoneResponse> {

    @Override
    public EvaluateMilestoneResponse Run(EvaluateMilestoneRequest request) {
        // First we will get the milestones and add them to the database first,
        // This will allow us to have their IDs for when we insert the contract.

        return null;

    }
}
