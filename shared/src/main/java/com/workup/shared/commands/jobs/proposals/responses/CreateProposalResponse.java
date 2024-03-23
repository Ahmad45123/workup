package com.workup.shared.commands.jobs.proposals.responses;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = CreateProposalResponse.CreateProposalResponseBuilder.class)
public class CreateProposalResponse extends CommandResponse {
    private final String id;
}
