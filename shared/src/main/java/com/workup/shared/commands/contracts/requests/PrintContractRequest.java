package com.workup.shared.commands.contracts.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

// PURPOSE: The purpose of this request is for the freelancer or the client
// to print a PDF which contains the details of the contract for this job, and of the
// client and freelancer.
@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class PrintContractRequest extends CommandRequest {
    private final String contractId;
}
