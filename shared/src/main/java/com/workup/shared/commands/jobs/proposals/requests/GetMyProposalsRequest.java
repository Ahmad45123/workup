package com.workup.shared.commands.jobs.proposals.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.workup.shared.commands.CommandRequest;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@JsonSerialize
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetMyProposalsRequest extends CommandRequest {
  // The proposals are fetched based on the currently logged in user
}
