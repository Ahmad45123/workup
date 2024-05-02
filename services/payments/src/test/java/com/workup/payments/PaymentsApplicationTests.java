package com.workup.payments;

import static org.junit.jupiter.api.Assertions.*;

import com.workup.payments.models.Wallet;
import com.workup.payments.models.WalletTransaction;
import com.workup.payments.repositories.PaymentRequestRepository;
import com.workup.payments.repositories.PaymentTransactionRepository;
import com.workup.payments.repositories.WalletRepository;
import com.workup.payments.repositories.WalletTransactionRepository;
import com.workup.shared.commands.payments.paymentrequest.requests.CreatePaymentRequestRequest;
import com.workup.shared.commands.payments.paymentrequest.responses.CreatePaymentRequestResponse;
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
import com.workup.shared.enums.payments.WalletTransactionType;
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
            .withDescription("Payment for services rendered")
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
              assertEquals("Payment for services rendered", paymentRequest.getDescription());
              assertEquals("3", paymentRequest.getClientId());
              assertEquals("4", paymentRequest.getFreelancerId());
            },
            () -> fail("Payment request not found"));
  }

  @Test
  void testCreateValidWalletTransactionRequest() {
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
  void testCreateDuplicatedWalletTransactionRequest() {
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
  void testGetWalletTransactionsRequest(){
    WalletTransaction walletTransaction1 =
            WalletTransaction.builder()
                    .withAmount(1000)
                    .withTransactionType(WalletTransactionType.DEBIT)
                    .withPaymentTransactionId("1")
                    .withWalletId("1")
                    .build();
    WalletTransaction savedWalletTransaction1 = walletTransactionRepository.save(walletTransaction1);

    WalletTransaction walletTransaction2 =
            WalletTransaction.builder()
                    .withAmount(800)
                    .withTransactionType(WalletTransactionType.DEBIT)
                    .withPaymentTransactionId("1")
                    .withWalletId(savedWalletTransaction1.getWalletId())
                    .build();
    WalletTransaction savedWalletTransaction2 = walletTransactionRepository.save(walletTransaction2);

    GetWalletTransactionsRequest getWalletTransactionsRequest = GetWalletTransactionsRequest.builder()
            .withFreelancerId(savedWalletTransaction1.getWalletId())
            .build();

    GetWalletTransactionsResponse response = (GetWalletTransactionsResponse) template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, getWalletTransactionsRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.OK, response.getStatusCode());
    assertEquals(2, response.getTransactions().size());
  }

  @Test
  void testWithdrawFromWalletRequest(){
    double balance = 1000;
    double withdrawAmount = 200;

    Wallet wallet = Wallet.builder()
            .withBalance(balance)
            .withFreelancerId("1")
            .build();
    Wallet savedWallet = walletRepository.save(wallet);

    WithdrawFromWalletRequest withdrawFromWalletRequest = WithdrawFromWalletRequest.builder()
            .withAmount(withdrawAmount)
            .withFreelancerId("1")
            .withPaymentTransactionId("1")
            .build();

    WithdrawFromWalletResponse response = (WithdrawFromWalletResponse) template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, withdrawFromWalletRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.OK, response.getStatusCode());

    walletRepository.findById(savedWallet.getFreelancerId())
            .ifPresentOrElse(
                    foundWallet -> {
                      assertEquals(balance - withdrawAmount , foundWallet.getBalance());
                    },
                    () -> fail("Wallet is not found")
            );

  }

  @Test
  void testNotFoundWithdrawFromWalletRequest(){

    Wallet wallet = Wallet.builder()
            .withBalance(1000)
            .withFreelancerId("1")
            .build();
    Wallet savedWallet = walletRepository.save(wallet);

    WithdrawFromWalletRequest withdrawFromWalletRequest = WithdrawFromWalletRequest.builder()
            .withAmount(200)
            .withFreelancerId("2")
            .withPaymentTransactionId("1")
            .build();

    WithdrawFromWalletResponse response = (WithdrawFromWalletResponse) template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, withdrawFromWalletRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.NOT_FOUND, response.getStatusCode());

  }

  @Test
  void testInvalidBiggerAmountWithdrawFromWalletRequest(){
    double balance = 1000;
    double withdrawAmount = 2000;

    Wallet wallet = Wallet.builder()
            .withBalance(balance)
            .withFreelancerId("1")
            .build();
    Wallet savedWallet = walletRepository.save(wallet);

    WithdrawFromWalletRequest withdrawFromWalletRequest = WithdrawFromWalletRequest.builder()
            .withAmount(withdrawAmount)
            .withFreelancerId("1")
            .withPaymentTransactionId("1")
            .build();

    WithdrawFromWalletResponse response = (WithdrawFromWalletResponse) template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, withdrawFromWalletRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.BAD_REQUEST, response.getStatusCode());

    walletRepository.findById(savedWallet.getFreelancerId())
            .ifPresentOrElse(
                    foundWallet -> {
                      assertEquals(balance  , foundWallet.getBalance());
                    },
                    () -> fail("Wallet is not found")
            );


  }
}
