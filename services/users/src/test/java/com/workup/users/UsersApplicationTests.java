package com.workup.users;

import static org.junit.Assert.*;

import com.workup.shared.commands.users.requests.*;
import com.workup.shared.commands.users.responses.*;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.shared.views.users.AchievementView;
import com.workup.shared.views.users.EducationView;
import com.workup.shared.views.users.ExperienceView;
import com.workup.users.db.Achievement;
import com.workup.users.db.Education;
import com.workup.users.db.Experience;
import com.workup.users.db.Freelancer;
import com.workup.users.repositories.*;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
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
  @Autowired private AchievementRepository achievementRepository;
  @Autowired private EducationRepository educationRepository;

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

  @Test
  void testGetProfileBrief() {
    var freelancerObj = freelancerRepository.save(UsersTestUtils.generateRandomFreelancer());

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

    var freelancerObj = freelancerRepository.save(UsersTestUtils.generateRandomFreelancer());

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
    var freelancerObj = freelancerRepository.save(UsersTestUtils.generateRandomFreelancer());

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

    var freelancerObj = freelancerRepository.save(UsersTestUtils.generateRandomFreelancer());

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

    var clientObj = paymentRequestRepository.save(UsersTestUtils.generateRandomClient());

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
    var clientObj = paymentRequestRepository.save(UsersTestUtils.generateRandomClient());

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

  @Test
  void testAddAchievement() {
    Freelancer freelancerObj = freelancerRepository.save(UsersTestUtils.createTestFreelancer());
    Achievement achievementObj = UsersTestUtils.createTestAchievement();
    AddFreelancerAchievementRequest request =
        AddFreelancerAchievementRequest.builder()
            .withUserId(freelancerObj.getId().toString())
            .withAchievement_description(achievementObj.getAchievement_description())
            .withAchievement_name(achievementObj.getAchievement_name())
            .withAward_date(achievementObj.getAward_date())
            .withAwarded_by(achievementObj.getAwarded_by())
            .build();
    AddFreelancerAchievementResponse response =
        (AddFreelancerAchievementResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.CREATED);
    freelancerObj = freelancerRepository.findById(freelancerObj.getId().toString()).get();
    Achievement addedAchievement = freelancerObj.getAchievements().get(0);
    UsersTestUtils.assertAchievementEquals(achievementObj, addedAchievement);
  }

  @Test
  void testAddEducation() {
    Freelancer freelancerObj = freelancerRepository.save(UsersTestUtils.createTestFreelancer());
    Education educationObj = UsersTestUtils.createTestEducation();
    AddFreelancerEducationRequest request =
        AddFreelancerEducationRequest.builder()
            .withUserId(freelancerObj.getId().toString())
            .withSchool_name(educationObj.getSchool_name())
            .withDegree(educationObj.getDegree())
            .withEducation_start_date(educationObj.getEducation_start_date())
            .withCity(educationObj.getCity())
            .withEnd_date(educationObj.getEnd_date())
            .withMajor(educationObj.getMajor())
            .withEducation_description(educationObj.getEducation_description())
            .withGrade(educationObj.getGrade())
            .build();
    AddFreelancerEducationResponse response =
        (AddFreelancerEducationResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.CREATED);
    freelancerObj = freelancerRepository.findById(freelancerObj.getId().toString()).get();
    Education addedEducation = freelancerObj.getEducations().get(0);
    UsersTestUtils.assertEducationEquals(educationObj, addedEducation);
  }

  @Test
  void testAddExperience() {
    Freelancer freelancerObj = freelancerRepository.save(UsersTestUtils.createTestFreelancer());
    Experience experienceObj = UsersTestUtils.createTestExperience();
    AddFreelancerExperienceRequest request =
        AddFreelancerExperienceRequest.builder()
            .withUserId(freelancerObj.getId().toString())
            .withCompany_name(experienceObj.getCompany_name())
            .withJob_title(experienceObj.getJob_title())
            .withEmployment_start(experienceObj.getEmployment_start())
            .withEmployment_end(experienceObj.getEmployment_end())
            .withCity(experienceObj.getCity())
            .withExperience_description(experienceObj.getExperience_description())
            .build();
    AddFreelancerExperienceResponse response =
        (AddFreelancerExperienceResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.CREATED);
    freelancerObj = freelancerRepository.findById(freelancerObj.getId().toString()).get();
    Experience addedExperience = freelancerObj.getExperiences().get(0);
    UsersTestUtils.assertExperienceEquals(experienceObj, addedExperience);
  }

  @Test
  void testAddSkill() {
    Freelancer freelancerObj = freelancerRepository.save(UsersTestUtils.createTestFreelancer());
    String skill = "Java";
    AddFreelancerSkillRequest request =
        AddFreelancerSkillRequest.builder()
            .withUserId(freelancerObj.getId().toString())
            .withNewSkill(skill)
            .build();
    AddFreelancerSkillResponse response =
        (AddFreelancerSkillResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.CREATED);
    freelancerObj = freelancerRepository.findById(freelancerObj.getId().toString()).get();
    List<String> addedSkills = freelancerObj.getSkills();
    Assertions.assertTrue(addedSkills.contains(skill));
  }

  @Test
  void testAddLanguage() {
    Freelancer freelancerObj = freelancerRepository.save(UsersTestUtils.createTestFreelancer());
    String language = "English";
    AddFreelancerLanguageRequest request =
        AddFreelancerLanguageRequest.builder()
            .withUserId(freelancerObj.getId().toString())
            .withNewLanguage(language)
            .build();
    AddFreelancerLanguageResponse response =
        (AddFreelancerLanguageResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.CREATED);
    freelancerObj = freelancerRepository.findById(freelancerObj.getId().toString()).get();
    List<String> addedLanguages = freelancerObj.getLanguages();
    Assertions.assertTrue(addedLanguages.contains(language));
  }

  @Test
  void testGetFreelancerAchievements() {
    Freelancer freelancerObj = freelancerRepository.save(UsersTestUtils.createTestFreelancer());
    Achievement achievementObj = achievementRepository.save(UsersTestUtils.createTestAchievement());
    freelancerObj.getAchievements().add(achievementObj);
    freelancerRepository.save(freelancerObj);

    GetFreelancerAchievementsRequest request =
        GetFreelancerAchievementsRequest.builder()
            .withUserId(freelancerObj.getId().toString())
            .build();

    GetFreelancerAchievementsResponse response =
        (GetFreelancerAchievementsResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.OK);
    List<AchievementView> achievements = response.getAchievements();
    Assertions.assertEquals(achievements.size(), 1);
    UsersTestUtils.assertAchievementEquals(achievementObj, achievements.get(0));
  }

  @Test
  void testGetFreelancerEducations() {
    Freelancer freelancerObj = freelancerRepository.save(UsersTestUtils.createTestFreelancer());
    Education educationObj = educationRepository.save(UsersTestUtils.createTestEducation());
    freelancerObj.getEducations().add(educationObj);
    freelancerRepository.save(freelancerObj);

    GetFreelancerEducationsRequest request =
        GetFreelancerEducationsRequest.builder()
            .withUserId(freelancerObj.getId().toString())
            .build();

    GetFreelancerEducationsResponse response =
        (GetFreelancerEducationsResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.OK);
    List<EducationView> educations = response.getEducations();
    Assertions.assertEquals(educations.size(), 1);
    UsersTestUtils.assertEducationEquals(educationObj, educations.get(0));
  }

  @Test
  void testGetFreelancerExperiences() {
    Freelancer freelancerObj = freelancerRepository.save(UsersTestUtils.createTestFreelancer());
    Experience experienceObj = experienceRepository.save(UsersTestUtils.createTestExperience());
    freelancerObj.getExperiences().add(experienceObj);
    freelancerRepository.save(freelancerObj);

    GetFreelancerExperiencesRequest request =
        GetFreelancerExperiencesRequest.builder()
            .withUserId(freelancerObj.getId().toString())
            .build();

    GetFreelancerExperiencesResponse response =
        (GetFreelancerExperiencesResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.OK);
    List<ExperienceView> experiences = response.getExperiences();
    Assertions.assertEquals(experiences.size(), 1);
    UsersTestUtils.assertExperienceEquals(experienceObj, experiences.getFirst());
  }

  @Test
  void testGetFreelancerSkills() {
    Freelancer freelancerObj = freelancerRepository.save(UsersTestUtils.createTestFreelancer());
    String skill = "Java";
    freelancerObj.getSkills().add(skill);
    freelancerRepository.save(freelancerObj);

    GetFreelancerSkillsRequest request =
        GetFreelancerSkillsRequest.builder().withUserId(freelancerObj.getId().toString()).build();

    GetFreelancerSkillsResponse response =
        (GetFreelancerSkillsResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.OK);
    List<String> skills = response.getSkills();
    Assertions.assertEquals(skills.size(), 1);
    Assertions.assertEquals(skill, skills.getFirst());
  }

  @Test
  void testGetFreelancerLanguages() {
    Freelancer freelancerObj = freelancerRepository.save(UsersTestUtils.createTestFreelancer());
    String language = "English";
    freelancerObj.getLanguages().add(language);
    freelancerRepository.save(freelancerObj);

    GetFreelancerLanguagesRequest request =
        GetFreelancerLanguagesRequest.builder()
            .withUserId(freelancerObj.getId().toString())
            .build();

    GetFreelancerLanguagesResponse response =
        (GetFreelancerLanguagesResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.OK);
    List<String> languages = response.getLanguages();
    Assertions.assertEquals(languages.size(), 1);
    Assertions.assertEquals(language, languages.getFirst());
  }
}
