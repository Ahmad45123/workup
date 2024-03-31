package com.workup.shared.commands.contracts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.enums.contracts.ContractState;
import lombok.Builder;

@Builder(setterPrefix = "with")
@JsonDeserialize
public class Contract {
    private final String contractId;

    private final String jobId;
    private final String proposalId;

    private final String freelancerId;
    private final String clientId;

    private final String[] milestones;
    private final ContractState status;

}
