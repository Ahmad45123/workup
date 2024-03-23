package com.workup.shared.commands.jobs.proposals.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = GetProposalsByJobIdRequest.GetProposalsByJobIdRequestBuilder.class)
public class GetProposalsByJobIdRequest extends CommandRequest {
    private final String jobId;
}
