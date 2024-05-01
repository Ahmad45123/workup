package com.workup.payments;

import com.workup.payments.commands.PaymentCommandMap;
import com.workup.payments.commands.paymentrequest.CreatePaymentRequestCommand;
import com.workup.payments.commands.wallet.CreateWalletCommand;
import com.workup.payments.commands.wallet.GetWalletCommand;
import com.workup.shared.commands.payments.paymentrequest.requests.CreatePaymentRequestRequest;
import com.workup.shared.commands.payments.paymentrequest.responses.CreatePaymentRequestResponse;
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

  @Autowired
  public PaymentCommandMap commandMap;

  @RabbitHandler
  public CreatePaymentRequestResponse receive(CreatePaymentRequestRequest in) throws Exception {
    return ((CreatePaymentRequestCommand) commandMap.getCommand("CreatePaymentRequest")).Run(in);
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
