package com.workup.shared.commands.contracts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.enums.contracts.ContractState;
import lombok.Builder;

@Builder(setterPrefix = "with")
@JsonDeserialize(builder = com.workup.shared.commands.contracts.Contract.class)
public class Contract {
    private final String contractId;

    private final String jobId;
    private final String proposalId;

    private final String freelancerId;
    private final String clientId;

    private final String[] milestonesIds;
    private final ContractState status;

}
