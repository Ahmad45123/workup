package com.workup.shared.commands.contracts.requests;


import com.workup.shared.commands.CommandRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class HandleTerminationRequest extends CommandRequest {

    private final String contractTerminationRequestId;

}
