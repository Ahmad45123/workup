package com.workup.shared.commands.contracts.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.contracts.Milestone;
import com.workup.shared.enums.contracts.TerminationRequestStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetPendingTerminationsResponse extends CommandResponse {
    private String requestId;

    private String contractId;
    private String requesterId;
    private String reason;

    private TerminationRequestStatus status;
}
