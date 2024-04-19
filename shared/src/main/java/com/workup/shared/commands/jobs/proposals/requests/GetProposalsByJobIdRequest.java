package com.workup.shared.commands.jobs.proposals.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.workup.shared.commands.CommandRequest;
import lombok.experimental.SuperBuilder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import lombok.ToString;

@Getter
@SuperBuilder(setterPrefix = "with")
@JsonSerialize
@Jacksonized
public class GetProposalsByJobIdRequest extends CommandRequest {
    private final String jobId;
}
