package com.workup.users;

import static org.junit.Assert.assertEquals;

import com.workup.shared.commands.users.requests.*;
import com.workup.shared.commands.users.responses.*;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.users.db.Client;
import com.workup.users.db.Freelancer;
import com.workup.users.repositories.ClientRepository;
import com.workup.users.repositories.ExperienceRepository;
import com.workup.users.repositories.FreelancerRepository;
import java.sql.Date;
import java.time.Instant;
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
    registry.add("spring.data.mongodb.uri", mongoDBContainer::getConnectionString);

    registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
    registry.add("spring.rabbitmq.port", rabbitMQContainer::getFirstMappedPort);
    registry.add("spring.rabbitmq.username", rabbitMQContainer::getAdminUsername);
    registry.add("spring.rabbitmq.password", rabbitMQContainer::getAdminPassword);
  }

  String randomString() {
    return java.util.UUID.randomUUID().toString();
  }

  // generate a freelancer with random details
  Freelancer generateFreelancer() {
    return Freelancer.builder()
        .withEmail(randomString() + "@gmail.com")
        .withFull_name(randomString())
        .withJob_title(randomString())
        .withCity(randomString())
        .withBirthdate(Date.from(Instant.now()))
        .withPassword_hash(randomString())
        .build();
  }

  Client generateClient() {
    return Client.builder()
        .withEmail(randomString() + "@gmail.com")
        .withClient_name(randomString())
        .withCity(randomString())
        .withIndustry(randomString())
        .withPhoto_link(randomString())
        .withClient_description(randomString())
        .withEmployee_count(10)
        .withPassword_hash(randomString())
        .build();
  }

  void assertEqualFreelancer(Freelancer freelancer, Freelancer freelancerObj) {
    assertEquals(freelancer.getEmail(), freelancerObj.getEmail());
    assertEquals(freelancer.getFull_name(), freelancerObj.getFull_name());
    assertEquals(freelancer.getJob_title(), freelancerObj.getJob_title());
    assertEquals(freelancer.getCity(), freelancerObj.getCity());
    assertEquals(freelancer.getBirthdate(), freelancerObj.getBirthdate());
    assertEquals(freelancer.getResume_link(), freelancerObj.getResume_link());
    assertEquals(freelancer.getPassword_hash(), freelancerObj.getPassword_hash());
  }

  void assertEqualClient(Client client, Client clientObj) {
    assertEquals(client.getEmail(), clientObj.getEmail());
    assertEquals(client.getClient_name(), clientObj.getClient_name());
    assertEquals(client.getCity(), clientObj.getCity());
    assertEquals(client.getIndustry(), clientObj.getIndustry());
    assertEquals(client.getPhoto_link(), clientObj.getPhoto_link());
    assertEquals(client.getClient_description(), clientObj.getClient_description());
    assertEquals(client.getEmployee_count(), clientObj.getEmployee_count());
    assertEquals(client.getPassword_hash(), clientObj.getPassword_hash());
  }

  @Test
  void testGetProfileBrief() {
    var freelancerObj = freelancerRepository.save(generateFreelancer());

    FreelancerGetProfileBriefRequest request =
        FreelancerGetProfileBriefRequest.builder()
            .withUser_id(freelancerObj.getId().toString())
            .build();

    FreelancerGetProfileBriefResponse breifResponse =
        (FreelancerGetProfileBriefResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);

    assertEquals(breifResponse.getStatusCode(), (HttpStatusCode.OK));
    assertEquals(breifResponse.getFull_name(), (freelancerObj.getFull_name()));
    assertEquals(breifResponse.getEmail(), (freelancerObj.getEmail()));
  }

  @Test
  void testFreelancerPhoto() {
    String photoLink = "https://www.google.com";

    var freelancerObj = freelancerRepository.save(generateFreelancer());

    FreelancerGetPhotoRequest request =
        FreelancerGetPhotoRequest.builder().withUser_id(freelancerObj.getId().toString()).build();

    FreelancerGetPhotoResponse photoResponse1 =
        (FreelancerGetPhotoResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);

    assertEquals(photoResponse1.getStatusCode(), (HttpStatusCode.OK));
    assertEquals(photoResponse1.getPhotoLink(), (freelancerObj.getPhoto_link()));

    freelancerObj.setPhoto_link(photoLink);
    freelancerRepository.save(freelancerObj);

    FreelancerGetPhotoResponse photoResponse2 =
        (FreelancerGetPhotoResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);

    assertEquals(photoResponse2.getStatusCode(), (HttpStatusCode.OK));
    assertEquals(photoResponse2.getPhotoLink(), (photoLink));
  }

  @Test
  void testFreelancerProfile() {
    var freelancerObj = freelancerRepository.save(generateFreelancer());

    FreelancerSetProfileRequest setRequest =
        FreelancerSetProfileRequest.builder()
            .withUser_id(freelancerObj.getId().toString())
            .withFull_name("John Doe")
            .withJob_title("Software Engineer")
            .withCity("New York")
            .withDescription("I am a software engineer")
            .withBirth_date(Date.from(Instant.now()))
            .withEmail("test@gmail.com")
            .build();

    FreelancerSetProfileResponse setResponse =
        (FreelancerSetProfileResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, setRequest);

    FreelancerGetProfileRequest getRequest =
        FreelancerGetProfileRequest.builder().withUser_id(freelancerObj.getId().toString()).build();

    FreelancerGetProfileResponse getResponse =
        (FreelancerGetProfileResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, getRequest);

    assertEquals(setResponse.getStatusCode(), (HttpStatusCode.OK));
    assertEquals(getResponse.getStatusCode(), (HttpStatusCode.OK));
    assertEquals(getResponse.getFull_name(), (setRequest.getFull_name()));
    assertEquals(getResponse.getJob_title(), (setRequest.getJob_title()));
    assertEquals(getResponse.getCity(), (setRequest.getCity()));
    assertEquals(getResponse.getDescription(), (setRequest.getDescription()));
    assertEquals(getResponse.getBirth_date(), (setRequest.getBirth_date()));
    assertEquals(getResponse.getEmail(), (setRequest.getEmail()));
  }

  @Test
  void testFreelancerResume() {
    String resumeLink = "https://www.google.com";

    var freelancerObj = freelancerRepository.save(generateFreelancer());

    FreelancerSetResumeRequest setRequest =
        FreelancerSetResumeRequest.builder()
            .withUser_id(freelancerObj.getId().toString())
            .withResumeLink(resumeLink)
            .build();

    FreelancerSetResumeResponse setResponse =
        (FreelancerSetResumeResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, setRequest);

    assertEquals(setResponse.getStatusCode(), (HttpStatusCode.OK));

    FreelancerGetResumeRequest getRequest =
        FreelancerGetResumeRequest.builder().withUser_id(freelancerObj.getId().toString()).build();

    FreelancerGetResumeResponse getResponse =
        (FreelancerGetResumeResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, getRequest);

    assertEquals(getResponse.getStatusCode(), (HttpStatusCode.OK));
    assertEquals(getResponse.getResumeLink(), (resumeLink));
  }

  @Test
  void testClientPhoto() {
    String photoLink = "https://www.google.com";

    var clientObj = paymentRequestRepository.save(generateClient());

    ClientGetPhotoRequest request =
        ClientGetPhotoRequest.builder().withUser_id(clientObj.getId().toString()).build();

    ClientGetPhotoResponse photoResponse1 =
        (ClientGetPhotoResponse) template.convertSendAndReceive(ServiceQueueNames.USERS, request);

    assertEquals(photoResponse1.getStatusCode(), (HttpStatusCode.OK));
    assertEquals(photoResponse1.getPhotoLink(), (clientObj.getPhoto_link()));

    clientObj.setPhoto_link(photoLink);
    paymentRequestRepository.save(clientObj);

    ClientGetPhotoResponse photoResponse2 =
        (ClientGetPhotoResponse) template.convertSendAndReceive(ServiceQueueNames.USERS, request);

    assertEquals(photoResponse2.getStatusCode(), (HttpStatusCode.OK));
    assertEquals(photoResponse2.getPhotoLink(), (photoLink));
  }

  @Test
  void testClientProfile() {
    var clientObj = paymentRequestRepository.save(generateClient());

    ClientSetProfileRequest setRequest =
        ClientSetProfileRequest.builder()
            .withUser_id(clientObj.getId().toString())
            .withName("John Doe")
            .withCity("New York")
            .withIndustry("Software")
            .withDescription("I am a software engineer")
            .withEmployee_count(10)
            .withEmail("test@gmail.com")
            .build();

    ClientSetProfileResponse setResponse =
        (ClientSetProfileResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, setRequest);

    ClientGetProfileRequest getRequest =
        ClientGetProfileRequest.builder().withUser_id(clientObj.getId().toString()).build();

    ClientGetProfileResponse getResponse =
        (ClientGetProfileResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, getRequest);

    assertEquals(setResponse.getStatusCode(), (HttpStatusCode.OK));
    assertEquals(getResponse.getStatusCode(), (HttpStatusCode.OK));
    assertEquals(getResponse.getName(), (setRequest.getName()));
    assertEquals(getResponse.getCity(), (setRequest.getCity()));
    assertEquals(getResponse.getIndustry(), (setRequest.getIndustry()));
    assertEquals(getResponse.getDescription(), (setRequest.getDescription()));
    assertEquals(getResponse.getEmployee_count(), (setRequest.getEmployee_count()));
    assertEquals(getResponse.getEmail(), (setRequest.getEmail()));
  }
}
