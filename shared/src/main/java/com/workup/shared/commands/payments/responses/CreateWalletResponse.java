package com.workup.shared.commands.payments.responses;

import com.workup.shared.commands.CommandResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.experimental.SuperBuilder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class CreateWalletResponse extends CommandResponse {
}
