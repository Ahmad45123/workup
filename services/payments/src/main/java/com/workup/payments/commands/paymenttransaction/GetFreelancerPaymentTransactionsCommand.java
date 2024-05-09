package com.workup.payments.commands.paymenttransaction;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.mapper.PaymentTransactionMapper;
import com.workup.payments.models.PaymentTransaction;
import com.workup.shared.commands.payments.dto.PaymentTransactionDTO;
import com.workup.shared.commands.payments.paymenttransaction.requests.GetFreelancerPaymentTransactionsRequest;
import com.workup.shared.commands.payments.paymenttransaction.responses.GetFreelancerPaymentTransactionsResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetFreelancerPaymentTransactionsCommand
    extends PaymentCommand<
        GetFreelancerPaymentTransactionsRequest, GetFreelancerPaymentTransactionsResponse> {
  private static final Logger logger =
      LogManager.getLogger(GetFreelancerPaymentTransactionsCommand.class);

  @Override
  public GetFreelancerPaymentTransactionsResponse Run(
      GetFreelancerPaymentTransactionsRequest request) {
    List<PaymentTransaction> savedTransactions =
        getPaymentTransactionRepository().findAllByFreelancerId(request.getFreelancerId());
    List<PaymentTransactionDTO> paymentTransactionDTOS =
        PaymentTransactionMapper.mapToPaymentTransactionDTOs(savedTransactions);

    logger.info("[x] Payment transactions fetched : " + paymentTransactionDTOS);

    return GetFreelancerPaymentTransactionsResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withTransactions(paymentTransactionDTOS)
        .build();
  }
}
