package com.workup.shared.commands.jobs.proposals;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonSerialize
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = AcceptedProposalInfo.AcceptedProposalInfoBuilder.class)
public class AcceptedProposalInfo {
    private final String id;
    private final ProposalStatus status;
}
