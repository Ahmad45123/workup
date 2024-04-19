package com.workup.shared.commands.contracts.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.contracts.Milestone;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

// PURPOSE: The purpose of this request is to initialize the creation of a contract from
// a proposal that is present in the jobs service. Since a contract IS the proposal made manifest,
// this endpoint will create a new Contract object in the database which contains all the
// information about this contract.
@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class InitiateContractRequest extends CommandRequest {
    private final String jobTitle;
    private final String jobId;
    private final String proposalId;

    private final String freelancerId;
    private final String clientId;

    private final Milestone[] jobMilestones;
}