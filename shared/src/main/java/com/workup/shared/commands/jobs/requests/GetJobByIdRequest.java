package com.workup.shared.commands.jobs.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetJobByIdRequest extends CommandRequest {

  private final String jobId;
}
