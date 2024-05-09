package com.workup.payments;

import static org.junit.jupiter.api.Assertions.*;

import com.workup.payments.models.PaymentRequest;
import com.workup.payments.models.PaymentTransaction;
import com.workup.payments.models.Wallet;
import com.workup.payments.models.WalletTransaction;
import com.workup.payments.repositories.PaymentRequestRepository;
import com.workup.payments.repositories.PaymentTransactionRepository;
import com.workup.payments.repositories.WalletRepository;
import com.workup.payments.repositories.WalletTransactionRepository;
import com.workup.shared.commands.payments.dto.PaymentRequestDTO;
import com.workup.shared.commands.payments.paymentrequest.requests.CreatePaymentRequestRequest;
import com.workup.shared.commands.payments.paymentrequest.requests.GetClientPaymentRequestsRequest;
import com.workup.shared.commands.payments.paymentrequest.requests.GetFreelancerPaymentRequestsRequest;
import com.workup.shared.commands.payments.paymentrequest.requests.PayPaymentRequestRequest;
import com.workup.shared.commands.payments.paymentrequest.responses.CreatePaymentRequestResponse;
import com.workup.shared.commands.payments.paymentrequest.responses.GetClientPaymentRequestsResponse;
import com.workup.shared.commands.payments.paymentrequest.responses.GetFreelancerPaymentRequestsResponse;
import com.workup.shared.commands.payments.paymentrequest.responses.PayPaymentRequestResponse;
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
import com.workup.shared.enums.payments.PaymentRequestStatus;
import com.workup.shared.enums.payments.PaymentTransactionStatus;
import com.workup.shared.enums.payments.WalletTransactionType;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@Import(TestConfigBase.class)
class PaymentsApplicationTests {

  @Container
  static final PostgreSQLContainer<?> postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:latest");

  @Container
  static final RabbitMQContainer rabbitMQContainer =
      new RabbitMQContainer("rabbitmq:3.13-management");

  @Autowired private AmqpTemplate template;
  @Autowired private PaymentRequestRepository paymentRequestRepository;
  @Autowired private PaymentTransactionRepository paymentTransactionRepository;
  @Autowired private WalletRepository walletRepository;
  @Autowired private WalletTransactionRepository walletTransactionRepository;

  @BeforeEach
  void clearAll() {
    paymentRequestRepository.deleteAll();
    paymentTransactionRepository.deleteAll();
    walletRepository.deleteAll();
    walletTransactionRepository.deleteAll();
  }

  @AfterAll
  static void stopContainers() {
    postgreSQLContainer.stop();
    rabbitMQContainer.stop();
  }

  @DynamicPropertySource
  static void setDatasourceProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");

    registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
    registry.add("spring.rabbitmq.port", rabbitMQContainer::getFirstMappedPort);
    registry.add("spring.rabbitmq.username", rabbitMQContainer::getAdminUsername);
    registry.add("spring.rabbitmq.password", rabbitMQContainer::getAdminPassword);
  }

  @Test
  void testCreatePaymentRequest() {
    CreatePaymentRequestRequest createPaymentRequest =
        CreatePaymentRequestRequest.builder()
            .withAmount(1200)
            .withReferenceId("Payment for services rendered")
            .withClientId("3")
            .withFreelancerId("4")
            .build();
    CreatePaymentRequestResponse response =
        (CreatePaymentRequestResponse)
            template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, createPaymentRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.CREATED, response.getStatusCode());

    paymentRequestRepository
        .findById(String.valueOf(UUID.fromString(response.getPaymentRequestId())))
        .ifPresentOrElse(
            paymentRequest -> {
              assertEquals(1200, paymentRequest.getAmount());
              assertEquals("Payment for services rendered", paymentRequest.getReferenceId());
              assertEquals("3", paymentRequest.getClientId());
              assertEquals("4", paymentRequest.getFreelancerId());
            },
            () -> fail("Payment request not found"));
  }

  @Test
  void testCreateValidWalletTransactionRequest() {
    Wallet wallet = Wallet.builder().withBalance(1000).withFreelancerId("1").build();
    walletRepository.save(wallet);
    CreateWalletTransactionRequest createWalletTransactionRequest =
        CreateWalletTransactionRequest.builder()
            .withAmount(1000)
            .withDescription("Not Empty Description")
            .withFreelancerId("1") // wallet ID
            .withPaymentTransactionId("2")
            .withTransactionType(WalletTransactionType.CREDIT)
            .build();

    CreateWalletTransactionResponse response =
        (CreateWalletTransactionResponse)
            template.convertSendAndReceive(
                ServiceQueueNames.PAYMENTS, createWalletTransactionRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.CREATED, response.getStatusCode());

    walletTransactionRepository
        .findById(response.getWalletTransactionId())
        .ifPresentOrElse(
            walletTransaction -> {
              System.out.println(walletTransaction.getWalletId());
              System.out.println(walletTransaction.getId());
              assertEquals(1000, walletTransaction.getAmount());
              assertEquals("1", walletTransaction.getWalletId());
              assertEquals("2", walletTransaction.getPaymentTransactionId());
              assertEquals(WalletTransactionType.CREDIT, walletTransaction.getTransactionType());
            },
            () -> fail("Wallet Transaction not found"));
  }

  @Test
  void testCreateInvalidNegativeWalletTransactionRequest() {
    Wallet wallet = Wallet.builder().withBalance(1000).withFreelancerId("1").build();
    Wallet savedWallet = walletRepository.save(wallet);

    CreateWalletTransactionRequest createWalletTransactionRequest =
        CreateWalletTransactionRequest.builder()
            .withAmount(-1000)
            .withDescription("Negative Balance")
            .withFreelancerId(savedWallet.getFreelancerId()) // wallet ID
            .withPaymentTransactionId("2")
            .withTransactionType(WalletTransactionType.CREDIT)
            .build();

    CreateWalletTransactionResponse response =
        (CreateWalletTransactionResponse)
            template.convertSendAndReceive(
                ServiceQueueNames.PAYMENTS, createWalletTransactionRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void testCreateNotFoundWalletTransactionRequest() {

    CreateWalletTransactionRequest createWalletTransactionRequest =
        CreateWalletTransactionRequest.builder()
            .withAmount(-1000)
            .withDescription("Negative Balance")
            .withFreelancerId("1") // wallet ID
            .withPaymentTransactionId("2")
            .withTransactionType(WalletTransactionType.CREDIT)
            .build();

    CreateWalletTransactionResponse response =
        (CreateWalletTransactionResponse)
            template.convertSendAndReceive(
                ServiceQueueNames.PAYMENTS, createWalletTransactionRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  @Test
  void testCreateDuplicatedWalletTransactionRequest() {
    Wallet wallet = Wallet.builder().withBalance(1000).withFreelancerId("1").build();
    walletRepository.save(wallet);

    CreateWalletTransactionRequest createWalletTransactionRequest =
        CreateWalletTransactionRequest.builder()
            .withAmount(1000)
            .withDescription("Not Empty Description")
            .withFreelancerId("1") // wallet ID
            .withPaymentTransactionId("2")
            .withTransactionType(WalletTransactionType.CREDIT)
            .build();

    CreateWalletTransactionResponse response =
        (CreateWalletTransactionResponse)
            template.convertSendAndReceive(
                ServiceQueueNames.PAYMENTS, createWalletTransactionRequest);
    assertNotNull(response);
    assertEquals(HttpStatusCode.CREATED, response.getStatusCode());
    // Should work just fine
    CreateWalletTransactionResponse response2 =
        (CreateWalletTransactionResponse)
            template.convertSendAndReceive(
                ServiceQueueNames.PAYMENTS, createWalletTransactionRequest);
    assertNotNull(response2);
    assertEquals(HttpStatusCode.CREATED, response2.getStatusCode());
  }

  @Test
  void testGetWalletTransactionRequest() {

    WalletTransaction walletTransaction =
        WalletTransaction.builder()
            .withAmount(1000)
            .withTransactionType(WalletTransactionType.DEBIT)
            .withPaymentTransactionId("1")
            .withWalletId("1")
            .build();
    WalletTransaction savedWalletTransaction = walletTransactionRepository.save(walletTransaction);

    GetWalletTransactionRequest getWalletTransactionRequest =
        GetWalletTransactionRequest.builder()
            .withWalletTransactionId(savedWalletTransaction.getId())
            .build();

    GetWalletTransactionResponse response =
        (GetWalletTransactionResponse)
            template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, getWalletTransactionRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.OK, response.getStatusCode());
  }

  @Test
  void testGetNonExistingWalletTransactionRequest() {
    GetWalletTransactionRequest getWalletTransactionRequest =
        GetWalletTransactionRequest.builder().withWalletTransactionId("1").build();
    GetWalletTransactionResponse response =
        (GetWalletTransactionResponse)
            template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, getWalletTransactionRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void testGetWalletTransactionsRequest() {
    WalletTransaction walletTransaction1 =
        WalletTransaction.builder()
            .withAmount(1000)
            .withTransactionType(WalletTransactionType.DEBIT)
            .withPaymentTransactionId("1")
            .withWalletId("1")
            .build();
    WalletTransaction savedWalletTransaction1 =
        walletTransactionRepository.save(walletTransaction1);

    WalletTransaction walletTransaction2 =
        WalletTransaction.builder()
            .withAmount(800)
            .withTransactionType(WalletTransactionType.DEBIT)
            .withPaymentTransactionId("1")
            .withWalletId(savedWalletTransaction1.getWalletId())
            .build();
    WalletTransaction savedWalletTransaction2 =
        walletTransactionRepository.save(walletTransaction2);

    GetWalletTransactionsRequest getWalletTransactionsRequest =
        GetWalletTransactionsRequest.builder()
            .withFreelancerId(savedWalletTransaction1.getWalletId())
            .build();

    GetWalletTransactionsResponse response =
        (GetWalletTransactionsResponse)
            template.convertSendAndReceive(
                ServiceQueueNames.PAYMENTS, getWalletTransactionsRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.OK, response.getStatusCode());
    assertEquals(2, response.getTransactions().size());
  }

  @Test
  void testWithdrawFromWalletRequest() {
    double balance = 1000;
    double withdrawAmount = 200;

    Wallet wallet = Wallet.builder().withBalance(balance).withFreelancerId("1").build();
    Wallet savedWallet = walletRepository.save(wallet);

    WithdrawFromWalletRequest withdrawFromWalletRequest =
        WithdrawFromWalletRequest.builder()
            .withAmount(withdrawAmount)
            .withFreelancerId("1")
            .build();

    WithdrawFromWalletResponse response =
        (WithdrawFromWalletResponse)
            template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, withdrawFromWalletRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.OK, response.getStatusCode());

    walletRepository
        .findById(savedWallet.getFreelancerId())
        .ifPresentOrElse(
            foundWallet -> {
              assertEquals(balance - withdrawAmount, foundWallet.getBalance());
            },
            () -> fail("Wallet is not found"));
  }

  @Test
  void testNotFoundWithdrawFromWalletRequest() {

    Wallet wallet = Wallet.builder().withBalance(1000).withFreelancerId("1").build();
    Wallet savedWallet = walletRepository.save(wallet);

    WithdrawFromWalletRequest withdrawFromWalletRequest =
        WithdrawFromWalletRequest.builder().withAmount(200).withFreelancerId("2").build();

    WithdrawFromWalletResponse response =
        (WithdrawFromWalletResponse)
            template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, withdrawFromWalletRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void testInvalidBiggerAmountWithdrawFromWalletRequest() {
    double balance = 1000;
    double withdrawAmount = 2000;

    Wallet wallet = Wallet.builder().withBalance(balance).withFreelancerId("1").build();
    Wallet savedWallet = walletRepository.save(wallet);

    WithdrawFromWalletRequest withdrawFromWalletRequest =
        WithdrawFromWalletRequest.builder()
            .withAmount(withdrawAmount)
            .withFreelancerId("1")
            .build();

    WithdrawFromWalletResponse response =
        (WithdrawFromWalletResponse)
            template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, withdrawFromWalletRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.BAD_REQUEST, response.getStatusCode());

    walletRepository
        .findById(savedWallet.getFreelancerId())
        .ifPresentOrElse(
            foundWallet -> {
              assertEquals(balance, foundWallet.getBalance());
            },
            () -> fail("Wallet is not found"));
  }

  @Test
  void testInvalidNegativeAmountWithdrawFromWalletRequest() {
    double balance = 1000;
    double withdrawAmount = -2000;

    Wallet wallet = Wallet.builder().withBalance(balance).withFreelancerId("1").build();
    Wallet savedWallet = walletRepository.save(wallet);

    WithdrawFromWalletRequest withdrawFromWalletRequest =
        WithdrawFromWalletRequest.builder()
            .withAmount(withdrawAmount)
            .withFreelancerId("1")
            .build();

    WithdrawFromWalletResponse response =
        (WithdrawFromWalletResponse)
            template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, withdrawFromWalletRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.BAD_REQUEST, response.getStatusCode());

    walletRepository
        .findById(savedWallet.getFreelancerId())
        .ifPresentOrElse(
            foundWallet -> {
              assertEquals(balance, foundWallet.getBalance());
              assertNotEquals(balance + withdrawAmount, foundWallet.getBalance());
            },
            () -> fail("Wallet is not found"));
  }

  @Test
  void testGetClientPaymentRequests() {
    PaymentRequest paymentRequest1 =
        PaymentRequest.builder()
            .withAmount(1200)
            .withReferenceId("Payment for services rendered")
            .withClientId("3")
            .withFreelancerId("4")
            .build();
    paymentRequestRepository.save(paymentRequest1);

    PaymentRequest paymentRequest2 =
        PaymentRequest.builder()
            .withAmount(3600)
            .withReferenceId("Payment for translation services")
            .withClientId("3")
            .withFreelancerId("10")
            .build();
    paymentRequestRepository.save(paymentRequest2);

    GetClientPaymentRequestsRequest getClientPaymentRequests =
        GetClientPaymentRequestsRequest.builder().withClientId("3").build();

    GetClientPaymentRequestsResponse response =
        (GetClientPaymentRequestsResponse)
            template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, getClientPaymentRequests);

    assertNotNull(response);
    assertEquals(HttpStatusCode.OK, response.getStatusCode());

    assertNotNull(response.getRequests());
    assertEquals(2, response.getRequests().size());

    PaymentRequestDTO requestDTO1 = response.getRequests().get(0);
    PaymentRequestDTO requestDTO2 = response.getRequests().get(1);

    assertAll(
        () -> assertEquals(paymentRequest1.getId(), requestDTO1.getId()),
        () -> assertEquals(paymentRequest1.getAmount(), requestDTO1.getAmount()),
        () -> assertEquals(paymentRequest1.getReferenceId(), requestDTO1.getReferenceId()),
        () -> assertEquals(paymentRequest1.getClientId(), requestDTO1.getClientId()),
        () -> assertEquals(paymentRequest1.getFreelancerId(), requestDTO1.getFreelancerId()),
        () -> assertEquals(paymentRequest2.getId(), requestDTO2.getId()),
        () -> assertEquals(paymentRequest2.getAmount(), requestDTO2.getAmount()),
        () -> assertEquals(paymentRequest2.getReferenceId(), requestDTO2.getReferenceId()),
        () -> assertEquals(paymentRequest2.getClientId(), requestDTO2.getClientId()),
        () -> assertEquals(paymentRequest2.getFreelancerId(), requestDTO2.getFreelancerId()));
  }

  @Test
  void testGetFreelancerPaymentRequests() {
    PaymentRequest paymentRequest1 =
        PaymentRequest.builder()
            .withAmount(1200)
            .withReferenceId("Payment for services rendered")
            .withClientId("3")
            .withFreelancerId("4")
            .build();
    paymentRequestRepository.save(paymentRequest1);

    PaymentRequest paymentRequest2 =
        PaymentRequest.builder()
            .withAmount(3600)
            .withReferenceId("Payment for translation services")
            .withClientId("10")
            .withFreelancerId("4")
            .build();
    paymentRequestRepository.save(paymentRequest2);

    GetFreelancerPaymentRequestsRequest getFreelancerPaymentRequests =
        GetFreelancerPaymentRequestsRequest.builder().withFreelancerId("4").build();

    GetFreelancerPaymentRequestsResponse response =
        (GetFreelancerPaymentRequestsResponse)
            template.convertSendAndReceive(
                ServiceQueueNames.PAYMENTS, getFreelancerPaymentRequests);

    assertNotNull(response);
    assertEquals(HttpStatusCode.OK, response.getStatusCode());

    assertNotNull(response.getRequests());
    assertEquals(2, response.getRequests().size());

    PaymentRequestDTO requestDTO1 = response.getRequests().get(0);
    PaymentRequestDTO requestDTO2 = response.getRequests().get(1);

    assertAll(
        () -> assertEquals(paymentRequest1.getId(), requestDTO1.getId()),
        () -> assertEquals(paymentRequest1.getAmount(), requestDTO1.getAmount()),
        () -> assertEquals(paymentRequest1.getReferenceId(), requestDTO1.getReferenceId()),
        () -> assertEquals(paymentRequest1.getClientId(), requestDTO1.getClientId()),
        () -> assertEquals(paymentRequest1.getFreelancerId(), requestDTO1.getFreelancerId()),
        () -> assertEquals(paymentRequest2.getId(), requestDTO2.getId()),
        () -> assertEquals(paymentRequest2.getAmount(), requestDTO2.getAmount()),
        () -> assertEquals(paymentRequest2.getReferenceId(), requestDTO2.getReferenceId()),
        () -> assertEquals(paymentRequest2.getClientId(), requestDTO2.getClientId()),
        () -> assertEquals(paymentRequest2.getFreelancerId(), requestDTO2.getFreelancerId()));
  }

  @Test
  void testPayPaymentRequest() {
    PaymentRequest paymentRequest =
        PaymentRequest.builder()
            .withAmount(1200)
            .withReferenceId("Payment for services rendered")
            .withClientId("3")
            .withFreelancerId("4")
            .build();
    paymentRequestRepository.save(paymentRequest);

    Wallet wallet = Wallet.builder().withFreelancerId("4").build();
    walletRepository.save(wallet);

    PayPaymentRequestRequest payPaymentRequest =
        PayPaymentRequestRequest.builder().withPaymentRequestId(paymentRequest.getId()).build();

    PayPaymentRequestResponse response =
        (PayPaymentRequestResponse)
            template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, payPaymentRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.OK, response.getStatusCode());

    assertNotNull(response.getTransactionId());
    assertNotNull(response.getTransactionStatus());

    assertEquals(PaymentTransactionStatus.SUCCESS, response.getTransactionStatus());

    Optional<PaymentTransaction> optionalPaymentTransaction =
        paymentTransactionRepository.findById(response.getTransactionId());

    assertTrue(optionalPaymentTransaction.isPresent());

    PaymentTransaction paymentTransaction = optionalPaymentTransaction.get();

    assertAll(
        () -> assertEquals(paymentRequest.getId(), paymentTransaction.getPaymentRequestId()),
        () -> assertEquals(paymentRequest.getAmount(), paymentTransaction.getAmount()),
        () -> assertEquals(PaymentTransactionStatus.SUCCESS, paymentTransaction.getStatus()));

    Optional<PaymentRequest> optionalUpdatedPaymentRequest =
        paymentRequestRepository.findById(paymentRequest.getId());

    assertTrue(optionalUpdatedPaymentRequest.isPresent());

    PaymentRequest updatedPaymentRequest = optionalUpdatedPaymentRequest.get();

    assertEquals(PaymentRequestStatus.PAID, updatedPaymentRequest.getStatus());

    Optional<Wallet> optionalUpdatedWallet = walletRepository.findById(wallet.getFreelancerId());

    assertTrue(optionalUpdatedWallet.isPresent());

    Wallet updatedWallet = optionalUpdatedWallet.get();

    assertEquals(1200, updatedWallet.getBalance());

    Optional<WalletTransaction> optionalWalletTransaction =
        walletTransactionRepository.findAll().stream()
            .filter(
                transaction ->
                    transaction.getWalletId().equals(wallet.getFreelancerId())
                        && transaction.getPaymentTransactionId().equals(paymentTransaction.getId()))
            .findFirst();

    assertTrue(optionalWalletTransaction.isPresent());

    WalletTransaction walletTransaction = optionalWalletTransaction.get();

    assertAll(
        () -> assertEquals(paymentRequest.getFreelancerId(), walletTransaction.getWalletId()),
        () -> assertEquals(paymentRequest.getAmount(), walletTransaction.getAmount()),
        () -> assertEquals(paymentTransaction.getId(), walletTransaction.getPaymentTransactionId()),
        () -> assertEquals(WalletTransactionType.CREDIT, walletTransaction.getTransactionType()));
  }

  @Test
  void testPayPaymentRequestNotFound() {
    PayPaymentRequestRequest payPaymentRequest =
        PayPaymentRequestRequest.builder().withPaymentRequestId("123").build();

    PayPaymentRequestResponse response =
        (PayPaymentRequestResponse)
            template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, payPaymentRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.NOT_FOUND, response.getStatusCode());
    assertNull(response.getTransactionId());
    assertNull(response.getTransactionStatus());
  }

  @Test
  void testPayPaymentRequestFreelancerWalletNotFound() {
    PaymentRequest paymentRequest =
        PaymentRequest.builder()
            .withAmount(1200)
            .withReferenceId("Payment for services rendered")
            .withClientId("3")
            .withFreelancerId("4")
            .build();
    paymentRequestRepository.save(paymentRequest);

    PayPaymentRequestRequest payPaymentRequest =
        PayPaymentRequestRequest.builder().withPaymentRequestId(paymentRequest.getId()).build();

    PayPaymentRequestResponse response =
        (PayPaymentRequestResponse)
            template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, payPaymentRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNull(response.getTransactionId());
    assertNull(response.getTransactionStatus());
  }

  @Test
  void testCreateWalletCommand() {

    CreateWalletRequest createWalletRequest =
        CreateWalletRequest.builder().withFreelancerId("1").build();
    CreateWalletResponse response =
        (CreateWalletResponse)
            template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, createWalletRequest);
    assertNotNull(response);
    assertEquals(HttpStatusCode.CREATED, response.getStatusCode());
  }

  @Test
  void testCreateDuplicateWalletIsInvalid() {
    CreateWalletRequest createWalletRequest =
        CreateWalletRequest.builder().withFreelancerId("1").build();
    CreateWalletResponse response =
        (CreateWalletResponse)
            template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, createWalletRequest);
    assertNotNull(response);
    assertEquals(HttpStatusCode.CREATED, response.getStatusCode());

    CreateWalletResponse response2 =
        (CreateWalletResponse)
            template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, createWalletRequest);
    assertNotNull(response2);
    assertEquals(HttpStatusCode.BAD_REQUEST, response2.getStatusCode());
  }

  @Test
  void testGetValidWallet() {
    CreateWalletRequest createWalletRequest =
        CreateWalletRequest.builder().withFreelancerId("1").build();
    CreateWalletResponse response =
        (CreateWalletResponse)
            template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, createWalletRequest);
    assertNotNull(response);
    assertEquals(HttpStatusCode.CREATED, response.getStatusCode());

    GetWalletRequest getWalletRequest = GetWalletRequest.builder().withFreelancerId("1").build();
    GetWalletResponse getWalletResponse =
        (GetWalletResponse)
            template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, getWalletRequest);
    assertNotNull(getWalletResponse);
    assertEquals(HttpStatusCode.OK, getWalletResponse.getStatusCode());
    assertEquals(0, getWalletResponse.getBalance());
  }

  @Test
  void testGetInvalidWallet() {
    GetWalletRequest getWalletRequest = GetWalletRequest.builder().withFreelancerId("1").build();
    GetWalletResponse getWalletResponse =
        (GetWalletResponse)
            template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, getWalletRequest);
    assertNotNull(getWalletResponse);
    assertEquals(HttpStatusCode.NOT_FOUND, getWalletResponse.getStatusCode());
  }
}
