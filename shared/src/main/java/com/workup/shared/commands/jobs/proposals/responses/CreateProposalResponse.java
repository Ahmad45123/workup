package com.workup.shared.commands.jobs.proposals.responses;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.workup.shared.commands.CommandResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonSerialize
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = CreateProposalResponse.CreateProposalResponseBuilder.class)
public class CreateProposalResponse extends CommandResponse {
    private final String id;
}
