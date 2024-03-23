package com.workup.shared.commands.jobs.proposals.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.workup.shared.commands.CommandRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder(setterPrefix = "with")
@JsonSerialize
@JsonDeserialize(builder = GetProposalsByJobIdRequest.GetProposalsByJobIdRequestBuilder.class)
public class GetProposalsByJobIdRequest extends CommandRequest {
    private final String jobId;
}
