package com.workup.shared.commands.payments.paymenttransaction.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.payments.dto.PaymentTransactionDTO;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class GetClientPaymentTransactionsResponse extends CommandResponse {

  private final List<PaymentTransactionDTO> transactions;
}
