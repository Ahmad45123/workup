package com.workup.payments;

import com.workup.payments.commands.PaymentCommandMap;
import com.workup.payments.commands.paymentrequest.CreatePaymentRequestCommand;
import com.workup.payments.commands.wallettransaction.CreateWalletTransactionCommand;
import com.workup.payments.commands.wallettransaction.GetWalletTransactionCommand;
import com.workup.shared.commands.payments.paymentrequest.requests.CreatePaymentRequestRequest;
import com.workup.shared.commands.payments.paymentrequest.responses.CreatePaymentRequestResponse;
import com.workup.shared.commands.payments.wallettransaction.requests.CreateWalletTransactionRequest;
import com.workup.shared.commands.payments.wallettransaction.requests.GetWalletTransactionRequest;
import com.workup.shared.commands.payments.wallettransaction.responses.CreateWalletTransactionResponse;
import com.workup.shared.commands.payments.wallettransaction.responses.GetWalletTransactionResponse;
import com.workup.shared.enums.ServiceQueueNames;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = ServiceQueueNames.PAYMENTS)
public class RabbitMQListener {

  @Autowired public PaymentCommandMap commandMap;

  @RabbitHandler
  public CreatePaymentRequestResponse receive(CreatePaymentRequestRequest in) throws Exception {
    return ((CreatePaymentRequestCommand) commandMap.getCommand("CreatePaymentRequest")).Run(in);
  }

  @RabbitHandler
  public CreateWalletTransactionResponse receive(CreateWalletTransactionRequest in)
      throws Exception {
    return ((CreateWalletTransactionCommand) commandMap.getCommand("CreateWalletTransaction"))
        .Run(in);
  }

  @RabbitHandler
  public GetWalletTransactionResponse receive(GetWalletTransactionRequest in) throws Exception {
    return ((GetWalletTransactionCommand) commandMap.getCommand("GetWalletTransaction")).Run(in);
  }
}
