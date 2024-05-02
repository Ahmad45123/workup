package com.workup.payments;

import com.workup.payments.commands.PaymentCommandMap;
import com.workup.payments.commands.paymentrequest.*;
import com.workup.payments.commands.wallet.CreateWalletCommand;
import com.workup.payments.commands.wallet.GetWalletCommand;
import com.workup.shared.commands.payments.paymentrequest.requests.*;
import com.workup.shared.commands.payments.paymentrequest.responses.*;
import com.workup.shared.commands.payments.wallet.requests.CreateWalletRequest;
import com.workup.shared.commands.payments.wallet.requests.GetWalletRequest;
import com.workup.shared.commands.payments.wallet.responses.CreateWalletResponse;
import com.workup.shared.commands.payments.wallet.responses.GetWalletResponse;
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
  public GetClientPaymentRequestsResponse receive(GetClientPaymentRequestsRequest in)
      throws Exception {
    return ((GetClientPaymentRequestsCommand) commandMap.getCommand("GetClientPaymentRequests"))
        .Run(in);
  }

  @RabbitHandler
  public GetFreelancerPaymentRequestsResponse receive(GetFreelancerPaymentRequestsRequest in)
      throws Exception {
    return ((GetFreelancerPaymentRequestsCommand)
            commandMap.getCommand("GetFreelancerPaymentRequests"))
        .Run(in);
  }

  @RabbitHandler
  public GetPaymentRequestResponse receive(GetPaymentRequestRequest in) throws Exception {
    return ((GetPaymentRequestCommand) commandMap.getCommand("GetPaymentRequest")).Run(in);
  }

  @RabbitHandler
  public PayPaymentRequestResponse receive(PayPaymentRequestRequest in) throws Exception {
    return ((PayPaymentRequestCommand) commandMap.getCommand("PayPaymentRequest")).Run(in);
  }

  @RabbitHandler
  public CreateWalletResponse receive(CreateWalletRequest in) throws Exception {
    return ((CreateWalletCommand) commandMap.getCommand("CreateWallet")).Run(in);
  }

  @RabbitHandler
  public GetWalletResponse receive(GetWalletRequest in) throws Exception {
    return ((GetWalletCommand) commandMap.getCommand("GetWallet")).Run(in);
  }
}
