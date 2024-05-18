package com.workup.users;

import com.workup.shared.commands.payments.wallet.requests.CreateWalletRequest;
import com.workup.shared.commands.payments.wallet.responses.CreateWalletResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import java.util.concurrent.CompletableFuture;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = ServiceQueueNames.PAYMENTS)
public class PaymentMockingListener {

  public static HttpStatusCode statusCodeToBeReturned;

  @RabbitHandler
  @Async
  public CompletableFuture<CreateWalletResponse> receive(CreateWalletRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          CreateWalletResponse.builder().withStatusCode(statusCodeToBeReturned).build());
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          CreateWalletResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }
}
