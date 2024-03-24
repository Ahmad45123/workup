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
@JsonDeserialize(builder = GetMyProposalsRequest.GetMyProposalsRequestBuilder.class)
public class GetMyProposalsRequest extends CommandRequest {
    // The proposals are fetched based on the currently logged in user
}
