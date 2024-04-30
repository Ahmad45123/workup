package com.workup.payments.commands.paymentrequest;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.mapper.PaymentRequestMapper;
import com.workup.payments.models.PaymentRequest;
import com.workup.shared.commands.payments.dto.PaymentRequestDTO;
import com.workup.shared.commands.payments.paymentrequest.requests.GetFreelancerPaymentRequestsRequest;
import com.workup.shared.commands.payments.paymentrequest.responses.GetFreelancerPaymentRequestsResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.List;

public class GetFreelancerPaymentRequestsCommand
    extends PaymentCommand<
        GetFreelancerPaymentRequestsRequest, GetFreelancerPaymentRequestsResponse> {

  @Override
  public GetFreelancerPaymentRequestsResponse Run(GetFreelancerPaymentRequestsRequest request) {
    if (!getPaymentRequestRepository().existsById(request.getFreelancerId())) {
      return GetFreelancerPaymentRequestsResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer not found")
          .build();
    }
    List<PaymentRequest> savedRequests =
        getPaymentRequestRepository().findAllByFreelancerId(request.getFreelancerId());

    List<PaymentRequestDTO> paymentRequestDTOS =
        PaymentRequestMapper.mapToPaymentRequestDTOs(savedRequests);

    System.out.println("[x] Payment requests fetched : " + paymentRequestDTOS);

    return GetFreelancerPaymentRequestsResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withRequests(paymentRequestDTOS)
        .build();
  }
}
