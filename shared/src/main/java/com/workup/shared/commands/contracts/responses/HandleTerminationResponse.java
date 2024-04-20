package com.workup.shared.commands.contracts.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.shared.enums.contracts.TerminationRequestStatus;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class HandleTerminationResponse extends CommandResponse {

    private final TerminationRequestStatus requestStatus;

}
