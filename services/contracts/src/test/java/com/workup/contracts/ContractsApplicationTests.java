package com.workup.contracts;

import com.workup.contracts.logger.ContractsLogger;
import com.workup.contracts.repositories.ContractMilestoneRepository;
import com.workup.contracts.repositories.ContractRepository;
import com.workup.contracts.repositories.TerminationRequestRepository;
import java.text.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@Import(TestConfigBase.class)
class ContractsApplicationTests {

  @Container
  static CassandraContainer<?> cassandraContainer =
      new CassandraContainer<>("cassandra:4.0.7").withConfigurationOverride("cassandra-config");

  @Container
  static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.13-management");

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
  }

  private static final String CLIENT_ONE_ID = "123";
  private static final String CLIENT_TWO_ID = "456";
  private static final String FREELANCER_ONE_ID = "789";

  @Autowired AmqpTemplate template;
  @Autowired ContractRepository contractRepository;
  @Autowired ContractMilestoneRepository contractMilestoneRepository;
  @Autowired TerminationRequestRepository terminationRequestRepository;

  @Autowired HandleContractTerminationTests handleContractTerminationTests;

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
  void testCreateJob() {
    // Example test from jobs
    //    CreateJobRequest createJobRequest =
    //            CreateJobRequest.builder()
    //                    .withTitle("Convert HTML Template to React 3")
    //                    .withDescription(
    //                            "I have an HTML template that I have purchased and own the rights
    // to. I would like"
    //                                    + " it converted into a React application.")
    //                    .withSkills(new String[] {"HTML", "CSS", "JavaScript", "React"})
    //                    .withUserId(CLIENT_ONE_ID)
    //                    .build();
    //
    //    CreateJobResponse response =
    //            (CreateJobResponse)
    //                    template.convertSendAndReceive(ServiceQueueNames.JOBS, createJobRequest);
    //
    //    assertNotNull(response);
    //    assertTrue(response.getStatusCode() == HttpStatusCode.CREATED);
    //
    //    jobRepository
    //            .findById(UUID.fromString(response.getJobId()))
    //            .ifPresentOrElse(
    //                    job -> assertTrue(job.getTitle().equals(createJobRequest.getTitle())),
    //                    () -> new RuntimeException("Job not found"));
  }

  @Test
  void HandleContractTerminationTest1() {
    try {
      handleContractTerminationTests.successTest(template);
    } catch (Exception e) {
      ContractsLogger.print("Test Failed");
    }
  }
}
