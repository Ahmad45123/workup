package com.workup.shared.commands.payments.paymentrequest.responses;

import com.workup.shared.commands.CommandResponse;
import com.workup.shared.enums.payments.PaymentTransactionStatus;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(setterPrefix = "with")
@Jacksonized
public class PayPaymentRequestResponse extends CommandResponse {

  private final String transactionId;
  private final PaymentTransactionStatus transactionStatus;
}
