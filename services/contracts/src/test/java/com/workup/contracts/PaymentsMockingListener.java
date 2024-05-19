package com.workup.contracts;

import com.workup.shared.commands.payments.paymentrequest.requests.CreatePaymentRequestRequest;
import com.workup.shared.commands.payments.paymentrequest.responses.CreatePaymentRequestResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = ServiceQueueNames.PAYMENTS)
public class PaymentsMockingListener {

  CreatePaymentRequestResponse receive(CreatePaymentRequestRequest req) {
    return CreatePaymentRequestResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }
}
