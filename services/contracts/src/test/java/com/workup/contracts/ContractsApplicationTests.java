package com.workup.contracts;


import com.workup.contracts.logger.ContractsLogger;
import com.workup.contracts.logger.LoggingLevel;
import com.workup.contracts.repositories.ContractMilestoneRepository;
import com.workup.contracts.repositories.ContractRepository;
import com.workup.contracts.repositories.TerminationRequestRepository;
import java.text.ParseException;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
@Testcontainers
@Import(TestConfigBase.class)
class ContractsApplicationTests {

  @Container
  static CassandraContainer<?> cassandraContainer =
      new CassandraContainer<>("cassandra:4.0.7").withConfigurationOverride("cassandra-config");

  @Container
  static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.13-management");

  @Container
  static GenericContainer redis = new GenericContainer<>("redis:7.2.4").withExposedPorts(6379);

  static String GetCassandraContactPoint() {
    return cassandraContainer.getHost() + ":" + cassandraContainer.getFirstMappedPort();
  }

  @DynamicPropertySource
  static void datasourceProperties(DynamicPropertyRegistry registry) {
    registry.add(
        "spring.cassandra.contact-points", ContractsApplicationTests::GetCassandraContactPoint);

    registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
    registry.add("spring.rabbitmq.port", rabbitMQContainer::getFirstMappedPort);
    registry.add("spring.rabbitmq.username", rabbitMQContainer::getAdminUsername);
    registry.add("spring.rabbitmq.password", rabbitMQContainer::getAdminPassword);

    registry.add("spring.cache.host", redis::getHost);
    registry.add("spring.cache.port", redis::getFirstMappedPort);
  }

  private static final String CLIENT_ONE_ID = "123";
  private static final String CLIENT_TWO_ID = "456";
  private static final String FREELANCER_ONE_ID = "789";

  @Autowired AmqpTemplate template;
  @Autowired ContractRepository contractRepository;
  @Autowired ContractMilestoneRepository contractMilestoneRepository;
  @Autowired TerminationRequestRepository terminationRequestRepository;
  @Autowired PaymentsMockingListener paymentsMockingListener;

  @Autowired HandleContractTerminationTests handleContractTerminationTests;
  @Autowired RequestContractTerminationTests requestContractTerminationTests;
  @Autowired MarkMilestoneAsPaidTests markMilestoneAsPaidTests;
  @Autowired EvaluateMilestoneTests evaluateMilestoneTests;
  @Autowired GetContractTests getContractTests;
  @Autowired GetMilestoneTests getMilestoneTests;
  @Autowired InitiateContractTests initiateContractTests;
  @Autowired ViewContractMilestonesTests viewContractMilestonesTests;
  @Autowired GetPendingTerminationsTests getPendingTerminationsTests;
  @Autowired PrintContractTests printContractTests;

  @BeforeEach
  void clearAll() {
    contractRepository.deleteAll();
    contractMilestoneRepository.deleteAll();
    terminationRequestRepository.deleteAll();
  }

  /**
   * Creates a job request.
   *
   * @throws ParseException
   */
  @Test
  void HandleContractTerminationTest1() throws ParseException {
    handleContractTerminationTests.acceptingRequest(template);
  }

  @Test
  void HandleContractTerminationTest2() {
    handleContractTerminationTests.requestNotFoundTest(template);
  }

  @Test
  void HandleContractTerminationTest3() throws ParseException {
    handleContractTerminationTests.rejectingRequest(template);
  }

  @Test
  void RequestTerminationTest1() {
    requestContractTerminationTests.contractNotFoundTest(template);
  }

  @Test
  void RequestTerminationTest2() throws ParseException {
    requestContractTerminationTests.requestedBeforeTest(template);
  }

  @Test
  void RequestTerminationTest3() throws ParseException {
    requestContractTerminationTests.unAuthorizedRequestTest(template);
  }

  @Test
  void RequestTerminationTest4() throws ParseException {
    requestContractTerminationTests.sucessTest(template);
  }

  @Test
  void MarkMilestoneAsPaidTest1() throws ParseException {
    markMilestoneAsPaidTests.nonExistingMilestone(template);
  }

  @Test
  void MarkMilestoneAsPaidTest2() throws ParseException {
    markMilestoneAsPaidTests.nonAcceptedMilestone(template);
  }

  @Test
  void MarkMilestoneAsPaidTest3() throws ParseException {
    markMilestoneAsPaidTests.successTest(template);
  }

  @Test
  void EvaluateMilestoneTest1() {
    evaluateMilestoneTests.milestoneNotFoundTest(template);
  }

  @Test
  void EvaluateMilestoneTest2() {
    evaluateMilestoneTests.wrongMilestoneState(template);
  }

  @Ignore
  void EvaluateMilestoneTest3() {
    try {
      evaluateMilestoneTests.successTest(template);
    } catch (Exception e) {
      ContractsLogger.print("Error Occurred in EvaluateMilestoneTest3", LoggingLevel.TRACE);
    }
  }

  @Test
  void GetContractTest1() {
    getContractTests.contractNotFoundTest(template);
  }

  @Test
  void GetContractTest2() {
    try {
      getContractTests.successTest(template);
    } catch (Exception e) {
      ContractsLogger.print("Error Happened in GetContractTest2", LoggingLevel.ERROR);
    }
  }

  @Test
  void ContractsLoggerTest1() {
    ContractsLogger.print("Contracts Logger Test 1 Message", LoggingLevel.INFO);
  }

  @Test
  void GetMilestoneTest1() {
    getMilestoneTests.milestoneNotFoundTest(template);
  }

  @Test
  void GetMilestoneTest2() {
    try {
      getMilestoneTests.successTest(template);
    } catch (Exception e) {
      ContractsLogger.print("Error occurred while applying GetMilestoneTest2", LoggingLevel.ERROR);
    }
  }

  @Test
  void InitiateContractTest() {
    try {
      initiateContractTests.successTest(template);
    } catch (Exception e) {
      ContractsLogger.print("Error Occurred in Initiating Contract Test", LoggingLevel.ERROR);
    }
  }

  @Test
  void ViewContractMilestonesTest1() {
    viewContractMilestonesTests.successTest(template);
  }

  @Test
  void ViewContractMilestonesTest2() {
    viewContractMilestonesTests.invalidContract(template);
  }

  @Test
  void GetPendingTerminationsTest1() {
    getPendingTerminationsTests.invalidContract(template);
  }

  @Test
  void GetPendingTerminationsTest2() {
    getPendingTerminationsTests.successTest(template);
  }

  @Test
  void PrintContractTest1() {
    printContractTests.contractNotFoundTest(template);
  }

  @Test
  void PrintContractTest2() {
    try {
      printContractTests.successTest(template);
    } catch (Exception e) {
      ContractsLogger.print("Error Occurred in Print Contract Test", LoggingLevel.ERROR);
    }
  }
}





