package com.workup.payments.commands.paymentrequest;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.mapper.PaymentRequestMapper;
import com.workup.payments.models.PaymentRequest;
import com.workup.shared.commands.payments.dto.PaymentRequestDTO;
import com.workup.shared.commands.payments.paymentrequest.requests.GetClientPaymentRequestsRequest;
import com.workup.shared.commands.payments.paymentrequest.responses.GetClientPaymentRequestsResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetClientPaymentRequestsCommand
    extends PaymentCommand<GetClientPaymentRequestsRequest, GetClientPaymentRequestsResponse> {
  private static final Logger logger = LogManager.getLogger(GetClientPaymentRequestsCommand.class);

  @Override
  public GetClientPaymentRequestsResponse Run(GetClientPaymentRequestsRequest request) {
    List<PaymentRequest> savedRequests =
        getPaymentRequestRepository().findAllByClientId(request.getClientId());

    List<PaymentRequestDTO> paymentRequestDTOS =
        PaymentRequestMapper.mapToPaymentRequestDTOs(savedRequests);

    logger.info("[x] Payment requests fetched : " + paymentRequestDTOS);

    return GetClientPaymentRequestsResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withRequests(paymentRequestDTOS)
        .build();
  }
}
