package com.workup.payments;

import static org.junit.jupiter.api.Assertions.*;

import com.workup.payments.repositories.PaymentRequestRepository;
import com.workup.payments.repositories.PaymentTransactionRepository;
import com.workup.payments.repositories.WalletRepository;
import com.workup.payments.repositories.WalletTransactionRepository;
import com.workup.shared.commands.payments.paymentrequest.requests.CreatePaymentRequestRequest;
import com.workup.shared.commands.payments.paymentrequest.responses.CreatePaymentRequestResponse;
import com.workup.shared.commands.payments.wallettransaction.requests.CreateWalletTransactionRequest;
import com.workup.shared.commands.payments.wallettransaction.requests.GetWalletTransactionRequest;
import com.workup.shared.commands.payments.wallettransaction.responses.CreateWalletTransactionResponse;
import com.workup.shared.commands.payments.wallettransaction.responses.GetWalletTransactionResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import java.util.UUID;

import com.workup.shared.enums.payments.WalletTransactionType;
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

    CreateWalletTransactionResponse response = (CreateWalletTransactionResponse) template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, createWalletTransactionRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.CREATED, response.getStatusCode());

    walletTransactionRepository.findById(String.valueOf(UUID.fromString(response.getWalletTransactionId())))
            .ifPresentOrElse(
                    walletTransaction -> {
                      System.out.println(walletTransaction.getWalletId());
                      System.out.println(walletTransaction.getId());
                      assertEquals(1000, walletTransaction.getAmount());
                      assertEquals("1", walletTransaction.getWalletId());
                      assertEquals("2", walletTransaction.getPaymentTransactionId());
                      assertEquals(WalletTransactionType.CREDIT, walletTransaction.getTransactionType());
                    },
                    () -> fail("Wallet Transaction not found")
            );
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

    CreateWalletTransactionResponse response = (CreateWalletTransactionResponse) template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, createWalletTransactionRequest);
    assertNotNull(response);
    assertEquals(HttpStatusCode.CREATED, response.getStatusCode());
    // Should work just fine
    CreateWalletTransactionResponse response2 = (CreateWalletTransactionResponse) template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, createWalletTransactionRequest);
    assertNotNull(response2);
    assertEquals(HttpStatusCode.CREATED, response2.getStatusCode());

  }

  @Test
  void testGetNonExistingWalletTransactionRequest(){
    GetWalletTransactionRequest getWalletTransactionRequest =
            GetWalletTransactionRequest.builder()
                    .withWalletTransactionId("1")
                    .withUserId("1")
                    .build();
    GetWalletTransactionResponse response = (GetWalletTransactionResponse) template.convertSendAndReceive(ServiceQueueNames.PAYMENTS, getWalletTransactionRequest);

    assertNotNull(response);
    assertEquals(HttpStatusCode.NOT_FOUND, response.getStatusCode());

  }


}
