package com.workup.shared.commands.jobs.proposals.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = AcceptProposalResponse.AcceptProposalResponseBuilder.class)
public class AcceptProposalResponse extends CommandResponse {
    private final String message;
    private final String jobId;
    private final String proposalId;
}
