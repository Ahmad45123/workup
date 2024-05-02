package com.workup.payments;

import static org.junit.jupiter.api.Assertions.*;

import com.workup.payments.repositories.PaymentRequestRepository;
import com.workup.payments.repositories.PaymentTransactionRepository;
import com.workup.payments.repositories.WalletRepository;
import com.workup.payments.repositories.WalletTransactionRepository;
import com.workup.shared.commands.payments.paymentrequest.requests.CreatePaymentRequestRequest;
import com.workup.shared.commands.payments.paymentrequest.responses.CreatePaymentRequestResponse;
import com.workup.shared.commands.payments.wallet.requests.CreateWalletRequest;
import com.workup.shared.commands.payments.wallet.requests.GetWalletRequest;
import com.workup.shared.commands.payments.wallet.responses.CreateWalletResponse;
import com.workup.shared.commands.payments.wallet.responses.GetWalletResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
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
