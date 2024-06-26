package com.workup.payments;

import com.workup.payments.commands.PaymentCommandMap;
import com.workup.payments.commands.paymentrequest.*;
import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandRequest;
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
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import java.util.concurrent.CompletableFuture;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = ServiceQueueNames.PAYMENTS, id = ServiceQueueNames.PAYMENTS)
public class RabbitMQListener {

  @Autowired public PaymentCommandMap commandMap;

  @RabbitHandler
  @Async
  public CompletableFuture<CreatePaymentRequestResponse> receive(CreatePaymentRequestRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (CreatePaymentRequestResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("CreatePaymentRequest")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          CreatePaymentRequestResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<CreateWalletTransactionResponse> receive(
      CreateWalletTransactionRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (CreateWalletTransactionResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("CreateWalletTransaction"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          CreateWalletTransactionResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetClientPaymentRequestsResponse> receive(
      GetClientPaymentRequestsRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (GetClientPaymentRequestsResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("GetClientPaymentRequests"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetClientPaymentRequestsResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetWalletTransactionResponse> receive(GetWalletTransactionRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (GetWalletTransactionResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("GetWalletTransaction")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetWalletTransactionResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetWalletTransactionsResponse> receive(GetWalletTransactionsRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (GetWalletTransactionsResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("GetWalletTransactions"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetWalletTransactionsResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<WithdrawFromWalletResponse> receive(WithdrawFromWalletRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (WithdrawFromWalletResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("WithdrawFromWallet")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          WithdrawFromWalletResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerPaymentRequestsResponse> receive(
      GetFreelancerPaymentRequestsRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (GetFreelancerPaymentRequestsResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("GetFreelancerPaymentRequests"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetFreelancerPaymentRequestsResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetPaymentRequestResponse> receive(GetPaymentRequestRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (GetPaymentRequestResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("GetPaymentRequest")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetPaymentRequestResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<PayPaymentRequestResponse> receive(PayPaymentRequestRequest in)
      throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (PayPaymentRequestResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("PayPaymentRequest")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          PayPaymentRequestResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<CreateWalletResponse> receive(CreateWalletRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (CreateWalletResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("CreateWallet")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          CreateWalletResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetWalletResponse> receive(GetWalletRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (GetWalletResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("GetWallet")).Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetWalletResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetClientPaymentTransactionsResponse> receive(
      GetClientPaymentTransactionsRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (GetClientPaymentTransactionsResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("GetClientPaymentTransactions"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetClientPaymentTransactionsResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetFreelancerPaymentTransactionsResponse> receive(
      GetFreelancerPaymentTransactionsRequest in) throws Exception {
    try {
      return CompletableFuture.completedFuture(
          (GetFreelancerPaymentTransactionsResponse)
              ((Command<CommandRequest, ?>)
                      commandMap.getCommand("GetFreelancerPaymentTransactions"))
                  .Run(in));
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetFreelancerPaymentTransactionsResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }
}
