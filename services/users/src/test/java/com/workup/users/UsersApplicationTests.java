package com.workup.users;

import com.workup.users.repositories.ClientRepository;
import com.workup.users.repositories.ExperienceRepository;
import com.workup.users.repositories.FreelancerRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@Import(TestConfigBase.class)
class UsersApplicationTests {

  @Container
  static final RabbitMQContainer rabbitMQContainer =
      new RabbitMQContainer("rabbitmq:3.13-management");

  @Container
  static final MongoDBContainer mongoDBContainer =
      new MongoDBContainer("mongo:7").withExposedPorts(27017);

  @Autowired private AmqpTemplate template;
  @Autowired private ClientRepository paymentRequestRepository;
  @Autowired private ExperienceRepository experienceRepository;
  @Autowired private FreelancerRepository freelancerRepository;

  @BeforeEach
  void clearAll() {
    paymentRequestRepository.deleteAll();
    experienceRepository.deleteAll();
    freelancerRepository.deleteAll();
  }

  @AfterAll
  static void stopContainers() {
    mongoDBContainer.stop();
    rabbitMQContainer.stop();
  }

  static int mongoport() {
    return mongoDBContainer.getMappedPort(27017);
  }

  @DynamicPropertySource
  static void setDatasourceProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
    registry.add("spring.data.mongodb.port", UsersApplicationTests::mongoport);
    // registry.add("spring.data.mongodb.database", mongoDBContainer::getname);

    registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
    registry.add("spring.rabbitmq.port", rabbitMQContainer::getFirstMappedPort);
    registry.add("spring.rabbitmq.username", rabbitMQContainer::getAdminUsername);
    registry.add("spring.rabbitmq.password", rabbitMQContainer::getAdminPassword);
  }

  @Test
  void testCreateUser() {}
}
