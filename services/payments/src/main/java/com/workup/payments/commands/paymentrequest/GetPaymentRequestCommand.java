package com.workup.payments.commands.paymentrequest;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.mapper.PaymentRequestMapper;
import com.workup.payments.models.PaymentRequest;
import com.workup.shared.commands.payments.dto.PaymentRequestDTO;
import com.workup.shared.commands.payments.paymentrequest.requests.GetPaymentRequestRequest;
import com.workup.shared.commands.payments.paymentrequest.responses.GetPaymentRequestResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.Optional;

public class GetPaymentRequestCommand
  extends PaymentCommand<GetPaymentRequestRequest, GetPaymentRequestResponse> {

  @Override
  public GetPaymentRequestResponse Run(GetPaymentRequestRequest request) {
    Optional<PaymentRequest> savedPaymentRequest = getPaymentRequestRepository()
      .findById(request.getPaymentRequestId());

    if (savedPaymentRequest.isEmpty()) {
      return GetPaymentRequestResponse
        .builder()
        .withStatusCode(HttpStatusCode.NOT_FOUND)
        .withErrorMessage("Payment request not found")
        .withRequest(null)
        .build();
    }

    System.out.println("[x] Payment request fetched : " + savedPaymentRequest.get());

    PaymentRequestDTO paymentRequestDTO = PaymentRequestMapper.mapToPaymentRequestDTO(
      savedPaymentRequest.get()
    );

    return GetPaymentRequestResponse
      .builder()
      .withStatusCode(HttpStatusCode.OK)
      .withRequest(paymentRequestDTO)
      .build();
  }
}
