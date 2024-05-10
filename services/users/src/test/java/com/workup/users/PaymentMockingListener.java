package com.workup.users;

import com.workup.shared.commands.payments.wallet.requests.CreateWalletRequest;
import com.workup.shared.commands.payments.wallet.responses.CreateWalletResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = ServiceQueueNames.PAYMENTS)
public class PaymentMockingListener {

  public static HttpStatusCode statusCodeToBeReturned;

  @RabbitHandler
  public CreateWalletResponse receive(CreateWalletRequest in) throws Exception {
    return CreateWalletResponse.builder().withStatusCode(statusCodeToBeReturned).build();
  }
}
