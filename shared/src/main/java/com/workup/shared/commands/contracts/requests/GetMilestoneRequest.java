package com.workup.shared.commands.contracts.requests;

import com.workup.shared.commands.CommandRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

// PURPOSE : To fulfill the need for the read of CRUD for our milestone repository
@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetMilestoneRequest extends CommandRequest {

  private final String milestoneId;
}
