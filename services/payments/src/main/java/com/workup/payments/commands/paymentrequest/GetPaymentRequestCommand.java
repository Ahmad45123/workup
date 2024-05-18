package com.workup.payments.commands.paymentrequest;

import com.workup.payments.commands.PaymentCommand;
import com.workup.payments.mapper.PaymentRequestMapper;
import com.workup.payments.models.PaymentRequest;
import com.workup.shared.commands.payments.dto.PaymentRequestDTO;
import com.workup.shared.commands.payments.paymentrequest.requests.GetPaymentRequestRequest;
import com.workup.shared.commands.payments.paymentrequest.responses.GetPaymentRequestResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.redis.RedisService;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetPaymentRequestCommand
    extends PaymentCommand<GetPaymentRequestRequest, GetPaymentRequestResponse> {
  private static final Logger logger = LogManager.getLogger(GetPaymentRequestCommand.class);

  @Override
  public GetPaymentRequestResponse Run(GetPaymentRequestRequest request) {
    RedisService redisService = getRedisService();

    GetPaymentRequestResponse cachedResponse =
        (GetPaymentRequestResponse)
            redisService.getValue(request.getPaymentRequestId(), GetPaymentRequestResponse.class);
    if (cachedResponse != null) {
      logger.info(
          "[x] Payment request response fetched from cache : " + cachedResponse.getRequest());

      return cachedResponse;
    }

    Optional<PaymentRequest> savedPaymentRequest =
        getPaymentRequestRepository().findById(request.getPaymentRequestId());

    if (savedPaymentRequest.isEmpty()) {
      return GetPaymentRequestResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Payment request not found")
          .withRequest(null)
          .build();
    }

    logger.info("[x] Payment request fetched : " + savedPaymentRequest.get());

    PaymentRequestDTO paymentRequestDTO =
        PaymentRequestMapper.mapToPaymentRequestDTO(savedPaymentRequest.get());

    GetPaymentRequestResponse response =
        GetPaymentRequestResponse.builder()
            .withStatusCode(HttpStatusCode.OK)
            .withRequest(paymentRequestDTO)
            .build();

    redisService.setValue(request.getPaymentRequestId(), response);

    return response;
  }
}
