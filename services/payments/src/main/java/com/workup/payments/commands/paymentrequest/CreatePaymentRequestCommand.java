package com.workup.payments.commands.paymentrequest;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.models.PaymentRequest;
import com.workup.shared.commands.payments.paymentrequest.requests.CreatePaymentRequestRequest;
import com.workup.shared.commands.payments.paymentrequest.responses.CreatePaymentRequestResponse;
import com.workup.shared.enums.HttpStatusCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreatePaymentRequestCommand
    extends PaymentCommand<CreatePaymentRequestRequest, CreatePaymentRequestResponse> {
  private static final Logger logger = LogManager.getLogger(CreatePaymentRequestCommand.class);

  @Override
  public CreatePaymentRequestResponse Run(CreatePaymentRequestRequest request) {
    PaymentRequest paymentRequest =
        PaymentRequest.builder()
            .withClientId(request.getClientId())
            .withFreelancerId(request.getFreelancerId())
            .withAmount(request.getAmount())
            .withReferenceId(request.getReferenceId())
            .build();
    try {
      PaymentRequest savedPaymentRequest = getPaymentRequestRepository().save(paymentRequest);

      logger.info("[x] Payment request created : " + savedPaymentRequest);

      return CreatePaymentRequestResponse.builder()
          .withStatusCode(HttpStatusCode.CREATED)
          .withPaymentRequestId(savedPaymentRequest.getId())
          .build();
    } catch (Exception e) {
      logger.error("[x] Payment request creation failed : " + e.getMessage());

      return CreatePaymentRequestResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage("An error occurred while creating payment request")
          .withPaymentRequestId(null)
          .build();
    }
  }
}
