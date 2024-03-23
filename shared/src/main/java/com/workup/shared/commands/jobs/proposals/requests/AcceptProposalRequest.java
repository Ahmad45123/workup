package com.workup.shared.commands.jobs.proposals.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.workup.shared.commands.CommandRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@JsonSerialize
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = AcceptProposalRequest.AcceptProposalRequestBuilder.class)
public class AcceptProposalRequest extends CommandRequest {
    private final String jobId;
    private final String proposalId;
}
