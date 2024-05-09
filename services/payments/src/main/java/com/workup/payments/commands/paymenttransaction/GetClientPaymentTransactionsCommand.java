package com.workup.payments.commands.paymenttransaction;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.mapper.PaymentTransactionMapper;
import com.workup.payments.models.PaymentTransaction;
import com.workup.shared.commands.payments.dto.PaymentTransactionDTO;
import com.workup.shared.commands.payments.paymenttransaction.requests.GetClientPaymentTransactionsRequest;
import com.workup.shared.commands.payments.paymenttransaction.responses.GetClientPaymentTransactionsResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetClientPaymentTransactionsCommand
    extends PaymentCommand<
        GetClientPaymentTransactionsRequest, GetClientPaymentTransactionsResponse> {
  private static final Logger logger =
      LogManager.getLogger(GetClientPaymentTransactionsCommand.class);

  @Override
  public GetClientPaymentTransactionsResponse Run(GetClientPaymentTransactionsRequest request) {
    List<PaymentTransaction> savedTransactions =
        getPaymentTransactionRepository().findAllByClientId(request.getClientId());
    List<PaymentTransactionDTO> paymentTransactionDTOS =
        PaymentTransactionMapper.mapToPaymentTransactionDTOs(savedTransactions);

    logger.info("[x] Payment transactions fetched : " + paymentTransactionDTOS);

    return GetClientPaymentTransactionsResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withTransactions(paymentTransactionDTOS)
        .build();
  }
}
