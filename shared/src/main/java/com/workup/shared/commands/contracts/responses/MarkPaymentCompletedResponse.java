package com.workup.shared.commands.contracts.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workup.shared.commands.CommandResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = com.workup.shared.commands.contracts.responses.MarkPaymentCompletedResponse.class)
public class MarkPaymentCompletedResponse extends CommandResponse {
}