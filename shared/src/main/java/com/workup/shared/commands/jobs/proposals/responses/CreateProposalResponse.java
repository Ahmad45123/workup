package com.workup.shared.commands.jobs.proposals.responses;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.workup.shared.commands.CommandResponse;
import lombok.experimental.SuperBuilder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@JsonSerialize
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class CreateProposalResponse extends CommandResponse {
    private final String id;
}
