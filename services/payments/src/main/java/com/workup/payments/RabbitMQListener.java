package com.workup.payments;

import com.workup.payments.commands.PaymentCommandMap;
import com.workup.payments.commands.paymentrequest.*;
import com.workup.payments.commands.paymenttransaction.GetClientPaymentTransactionsCommand;
import com.workup.payments.commands.paymenttransaction.GetFreelancerPaymentTransactionsCommand;
import com.workup.payments.commands.wallet.CreateWalletCommand;
import com.workup.payments.commands.wallet.GetWalletCommand;
import com.workup.payments.commands.wallettransaction.CreateWalletTransactionCommand;
import com.workup.payments.commands.wallettransaction.GetWalletTransactionCommand;
import com.workup.payments.commands.wallettransaction.GetWalletTransactionsCommand;
import com.workup.payments.commands.wallettransaction.WithdrawFromWalletCommand;
import com.workup.shared.commands.payments.paymentrequest.requests.*;
import com.workup.shared.commands.payments.paymentrequest.responses.*;
import com.workup.shared.commands.payments.paymenttransaction.requests.GetClientPaymentTransactionsRequest;
import com.workup.shared.commands.payments.paymenttransaction.requests.GetFreelancerPaymentTransactionsRequest;
import com.workup.shared.commands.payments.paymenttransaction.responses.GetClientPaymentTransactionsResponse;
import com.workup.shared.commands.payments.paymenttransaction.responses.GetFreelancerPaymentTransactionsResponse;
import com.workup.shared.commands.payments.wallet.requests.CreateWalletRequest;
import com.workup.shared.commands.payments.wallet.requests.GetWalletRequest;
import com.workup.shared.commands.payments.wallet.responses.CreateWalletResponse;
import com.workup.shared.commands.payments.wallet.responses.GetWalletResponse;
import com.workup.shared.commands.payments.wallettransaction.requests.CreateWalletTransactionRequest;
import com.workup.shared.commands.payments.wallettransaction.requests.GetWalletTransactionRequest;
import com.workup.shared.commands.payments.wallettransaction.requests.GetWalletTransactionsRequest;
import com.workup.shared.commands.payments.wallettransaction.requests.WithdrawFromWalletRequest;
import com.workup.shared.commands.payments.wallettransaction.responses.CreateWalletTransactionResponse;
import com.workup.shared.commands.payments.wallettransaction.responses.GetWalletTransactionResponse;
import com.workup.shared.commands.payments.wallettransaction.responses.GetWalletTransactionsResponse;
import com.workup.shared.commands.payments.wallettransaction.responses.WithdrawFromWalletResponse;
import com.workup.shared.enums.ServiceQueueNames;

import java.util.concurrent.CompletableFuture;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = ServiceQueueNames.PAYMENTS)
public class RabbitMQListener {

  @Autowired public PaymentCommandMap commandMap;

  @RabbitHandler
  @Async
  public CompletableFuture<CreatePaymentRequestResponse> receive(CreatePaymentRequestRequest in) throws Exception {
    return CompletableFuture.completedFuture(((CreatePaymentRequestCommand) commandMap.getCommand("CreatePaymentRequest")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<CreateWalletTransactionResponse> receive(CreateWalletTransactionRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(((CreateWalletTransactionCommand) commandMap.getCommand("CreateWalletTransaction"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetClientPaymentRequestsResponse> receive(GetClientPaymentRequestsRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(((GetClientPaymentRequestsCommand) commandMap.getCommand("GetClientPaymentRequests"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetWalletTransactionResponse> receive(GetWalletTransactionRequest in) throws Exception {
    return CompletableFuture.completedFuture(((GetWalletTransactionCommand) commandMap.getCommand("GetWalletTransaction")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetWalletTransactionsResponse> receive(GetWalletTransactionsRequest in) throws Exception {
    return CompletableFuture.completedFuture(((GetWalletTransactionsCommand) commandMap.getCommand("GetWalletTransactions")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<WithdrawFromWalletResponse> receive(WithdrawFromWalletRequest in) throws Exception {
    return CompletableFuture.completedFuture(((WithdrawFromWalletCommand) commandMap.getCommand("WithdrawFromWallet")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerPaymentRequestsResponse> receive(GetFreelancerPaymentRequestsRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(((GetFreelancerPaymentRequestsCommand)
            commandMap.getCommand("GetFreelancerPaymentRequests"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetPaymentRequestResponse> receive(GetPaymentRequestRequest in) throws Exception {
    return CompletableFuture.completedFuture(((GetPaymentRequestCommand) commandMap.getCommand("GetPaymentRequest")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<PayPaymentRequestResponse> receive(PayPaymentRequestRequest in) throws Exception {
    return CompletableFuture.completedFuture(((PayPaymentRequestCommand) commandMap.getCommand("PayPaymentRequest")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<CreateWalletResponse> receive(CreateWalletRequest in) throws Exception {
    return CompletableFuture.completedFuture(((CreateWalletCommand) commandMap.getCommand("CreateWallet")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetWalletResponse> receive(GetWalletRequest in) throws Exception {
    return CompletableFuture.completedFuture(((GetWalletCommand) commandMap.getCommand("GetWallet")).Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetClientPaymentTransactionsResponse> receive(GetClientPaymentTransactionsRequest in)
      throws Exception {
    return CompletableFuture.completedFuture(((GetClientPaymentTransactionsCommand)
            commandMap.getCommand("GetClientPaymentTransactions"))
        .Run(in));
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerPaymentTransactionsResponse> receive(
      GetFreelancerPaymentTransactionsRequest in) throws Exception {
    return CompletableFuture.completedFuture(((GetFreelancerPaymentTransactionsCommand)
            commandMap.getCommand("GetFreelancerPaymentTransactions"))
        .Run(in));
  }
}
