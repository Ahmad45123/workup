package com.workup.shared.commands.contracts.requests;

import com.workup.shared.commands.CommandRequest;
import com.workup.shared.enums.contracts.TerminationRequestStatus;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

// PURPOSE : To fulfill the need for the read of CRUD for our contract repository
@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetContractRequest extends CommandRequest {
    private final String contractId;
}
