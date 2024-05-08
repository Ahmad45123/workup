package com.workup.webserver.controller;

import com.workup.shared.commands.payments.paymentrequest.requests.GetClientPaymentRequestsRequest;
import com.workup.shared.commands.payments.paymentrequest.requests.GetFreelancerPaymentRequestsRequest;
import com.workup.shared.commands.payments.paymentrequest.requests.PayPaymentRequestRequest;
import com.workup.shared.commands.payments.paymentrequest.responses.GetClientPaymentRequestsResponse;
import com.workup.shared.commands.payments.paymentrequest.responses.GetFreelancerPaymentRequestsResponse;
import com.workup.shared.commands.payments.paymentrequest.responses.PayPaymentRequestResponse;
import com.workup.shared.commands.payments.paymenttransaction.requests.GetClientPaymentTransactionsRequest;
import com.workup.shared.commands.payments.paymenttransaction.requests.GetFreelancerPaymentTransactionsRequest;
import com.workup.shared.commands.payments.paymenttransaction.responses.GetClientPaymentTransactionsResponse;
import com.workup.shared.commands.payments.paymenttransaction.responses.GetFreelancerPaymentTransactionsResponse;
import com.workup.shared.commands.payments.wallet.requests.GetWalletRequest;
import com.workup.shared.commands.payments.wallet.responses.GetWalletResponse;
import com.workup.shared.commands.payments.wallettransaction.requests.WithdrawFromWalletRequest;
import com.workup.shared.commands.payments.wallettransaction.responses.WithdrawFromWalletResponse;
import com.workup.shared.enums.ServiceQueueNames;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/payments")
public class PaymentsController {
  @Autowired private AmqpTemplate rabbitTemplate;

  @GetMapping("/clients/me/requests")
  public ResponseEntity<GetClientPaymentRequestsResponse> getClientPaymentRequest(
      @RequestAttribute(name = "userId") String userId) {
    GetClientPaymentRequestsRequest request =
        GetClientPaymentRequestsRequest.builder().withClientId(userId).withUserId(userId).build();
    GetClientPaymentRequestsResponse response =
        (GetClientPaymentRequestsResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.PAYMENTS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @GetMapping("/clients/me/transactions")
  public ResponseEntity<GetClientPaymentTransactionsResponse> getClientPaymentTransaction(
      @RequestAttribute(name = "userId") String userId) {
    GetClientPaymentTransactionsRequest request =
        GetClientPaymentTransactionsRequest.builder()
            .withClientId(userId)
            .withUserId(userId)
            .build();
    GetClientPaymentTransactionsResponse response =
        (GetClientPaymentTransactionsResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.PAYMENTS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @GetMapping("/freelancers/me/requests")
  public ResponseEntity<GetFreelancerPaymentRequestsResponse> getFreelancerPaymentRequest(
      @RequestAttribute(name = "userId") String userId) {
    GetFreelancerPaymentRequestsRequest request =
        GetFreelancerPaymentRequestsRequest.builder()
            .withFreelancerId(userId)
            .withUserId(userId)
            .build();
    GetFreelancerPaymentRequestsResponse response =
        (GetFreelancerPaymentRequestsResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.PAYMENTS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @GetMapping("/freelancers/me/transactions")
  public ResponseEntity<GetFreelancerPaymentTransactionsResponse> getFreelancerPaymentTransaction(
      @RequestAttribute(name = "userId") String userId) {
    GetFreelancerPaymentTransactionsRequest request =
        GetFreelancerPaymentTransactionsRequest.builder()
            .withFreelancerId(userId)
            .withUserId(userId)
            .build();
    GetFreelancerPaymentTransactionsResponse response =
        (GetFreelancerPaymentTransactionsResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.PAYMENTS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @GetMapping("/freelancers/me/wallet")
  public ResponseEntity<GetWalletResponse> getFreelancerWallet(
      @RequestAttribute(name = "userId") String userId) {
    GetWalletRequest request =
        GetWalletRequest.builder().withFreelancerId(userId).withUserId(userId).build();
    GetWalletResponse response =
        (GetWalletResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.PAYMENTS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @PostMapping("/freelancers/me/wallet/withdraw")
  public ResponseEntity<WithdrawFromWalletResponse> withdrawFromWallet(
      @RequestAttribute(name = "userId") String userId,
      @RequestBody WithdrawFromWalletRequest request) {
    request.setFreelancerId(userId);
    request.setUserId(userId);
    WithdrawFromWalletResponse response =
        (WithdrawFromWalletResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.PAYMENTS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @PostMapping("/requests/{requestId}/pay")
  public ResponseEntity<PayPaymentRequestResponse> postMethodName(
      @PathVariable(name = "requestId") String requestId,
      @RequestAttribute(name = "userId") String userId) {
    PayPaymentRequestRequest request =
        PayPaymentRequestRequest.builder()
            .withPaymentRequestId(requestId)
            .withUserId(userId)
            .build();
    PayPaymentRequestResponse response =
        (PayPaymentRequestResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.PAYMENTS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }
}
