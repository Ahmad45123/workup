package com.workup.shared.commands.jobs.requests;

import com.workup.shared.commands.CommandRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder(setterPrefix = "with")
@Getter
@Jacksonized
public class GetMyJobsRequest extends CommandRequest {}
