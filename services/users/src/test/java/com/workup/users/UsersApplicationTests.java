package com.workup.users;

import static org.junit.Assert.assertEquals;

import com.workup.shared.commands.users.requests.*;
import com.workup.shared.commands.users.responses.*;
import com.workup.shared.enums.AdminUserCredentials;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.shared.enums.users.UserType;
import com.workup.shared.views.users.AchievementView;
import com.workup.shared.views.users.EducationView;
import com.workup.shared.views.users.ExperienceView;
import com.workup.users.commands.utils.PasswordHasher;
import com.workup.users.db.*;
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
import org.testcontainers.containers.GenericContainer;
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
      new MongoDBContainer("mongo:7.0").withExposedPorts(27017);

  // redis container
  @Container
  static final GenericContainer redisContainer =
      new GenericContainer("redis:7.2.4").withExposedPorts(6379);

  @Autowired private AmqpTemplate template;
  @Autowired private ClientRepository paymentRequestRepository;
  @Autowired private ExperienceRepository experienceRepository;
  @Autowired private FreelancerRepository freelancerRepository;
  @Autowired private AchievementRepository achievementRepository;
  @Autowired private EducationRepository educationRepository;
  @Autowired private ClientRepository clientRepository;

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
    // add redis properties
    registry.add("spring.cache.host", redisContainer::getHost);
    registry.add("spring.cache.port", redisContainer::getFirstMappedPort);
  }

  @Test
  public void testFreelancerRegister() {
    Freelancer freelancer = UsersTestUtils.generateRandomFreelancer();
    FreelancerRegisterRequest request =
        FreelancerRegisterRequest.builder()
            .withEmail(freelancer.getEmail())
            .withPassword(freelancer.getPassword_hash())
            .withFullName(freelancer.getFullName())
            .withCity(freelancer.getCity())
            .withJobTitle(freelancer.getJob_title())
            .withBirthDate(freelancer.getBirthdate())
            .build();
    SignUpAndInResponse response =
        (SignUpAndInResponse) template.convertSendAndReceive(ServiceQueueNames.USERS, request);
    UsersTestUtils.verifySignUpAndInResponse(response, freelancer.getEmail(), UserType.FREELANCER);
  }

  @Test
  public void testFreelancerLogin() {
    String password = "password";
    String hashedPassword = PasswordHasher.hashPassword(password);
    Freelancer randomFreelancer = UsersTestUtils.generateRandomFreelancer();
    randomFreelancer.setPassword_hash(hashedPassword);
    Freelancer freelancer = freelancerRepository.save(randomFreelancer);
    LoginRequest request =
        LoginRequest.builder().withEmail(freelancer.getEmail()).withPassword(password).build();
    SignUpAndInResponse response =
        (SignUpAndInResponse) template.convertSendAndReceive(ServiceQueueNames.USERS, request);
    UsersTestUtils.verifySignUpAndInResponse(response, freelancer.getEmail(), UserType.FREELANCER);
  }

  @Test
  public void testClientRegister() {
    Client client = UsersTestUtils.generateRandomClient();
    ClientRegisterRequest request =
        ClientRegisterRequest.builder()
            .withEmail(client.getEmail())
            .withPassword(client.getPassword_hash())
            .withCity(client.getCity())
            .withDescription(client.getClient_description())
            .withClientName(client.getClient_name())
            .withEmployeeCount(client.getEmployee_count())
            .withIndustry(client.getIndustry())
            .build();
    SignUpAndInResponse response =
        (SignUpAndInResponse) template.convertSendAndReceive(ServiceQueueNames.USERS, request);
    UsersTestUtils.verifySignUpAndInResponse(response, client.getEmail(), UserType.CLIENT);
  }

  @Test
  public void testAdminLogin() {
    LoginRequest request =
        LoginRequest.builder()
            .withEmail(AdminUserCredentials.ADMIN_EMAIL)
            .withPassword(AdminUserCredentials.ADMIN_PASSWORD)
            .build();
    SignUpAndInResponse response =
        (SignUpAndInResponse) template.convertSendAndReceive(ServiceQueueNames.USERS, request);
    UsersTestUtils.verifySignUpAndInResponse(
        response, AdminUserCredentials.ADMIN_EMAIL, UserType.ADMIN);
  }

  @Test
  public void testClientLogin() {
    String password = "password";
    String hashedPassword = PasswordHasher.hashPassword(password);
    Client randomClient = UsersTestUtils.generateRandomClient();
    randomClient.setPassword_hash(hashedPassword);
    Client client = clientRepository.save(randomClient);
    LoginRequest request =
        LoginRequest.builder().withEmail(client.getEmail()).withPassword(password).build();
    SignUpAndInResponse response =
        (SignUpAndInResponse) template.convertSendAndReceive(ServiceQueueNames.USERS, request);
    UsersTestUtils.verifySignUpAndInResponse(response, client.getEmail(), UserType.CLIENT);
  }

  @Test
  void testGetProfileBrief() {
    var freelancerObj = freelancerRepository.save(UsersTestUtils.generateRandomFreelancer());

    FreelancerGetProfileBriefRequest request =
        FreelancerGetProfileBriefRequest.builder()
            .withUserId(freelancerObj.getId().toString())
            .build();

    FreelancerGetProfileBriefResponse breifResponse =
        (FreelancerGetProfileBriefResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);

    assertEquals(breifResponse.getStatusCode(), (HttpStatusCode.OK));
    assertEquals(breifResponse.getFullName(), (freelancerObj.getFullName()));
    assertEquals(breifResponse.getEmail(), (freelancerObj.getEmail()));
  }

  @Test
  void testFreelancerPhoto() {
    String photoLink = "https://www.google.com";

    var freelancerObj = freelancerRepository.save(UsersTestUtils.generateRandomFreelancer());

    FreelancerGetPhotoRequest request =
        FreelancerGetPhotoRequest.builder().withUserId(freelancerObj.getId().toString()).build();

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
            .withUserId(freelancerObj.getId().toString())
            .withFullName("John Doe")
            .withJobTitle("Software Engineer")
            .withCity("New York")
            .withDescription("I am a software engineer")
            .withBirthDate(Date.from(Instant.now()))
            .withEmail("test@gmail.com")
            .build();

    FreelancerSetProfileResponse setResponse =
        (FreelancerSetProfileResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, setRequest);

    FreelancerGetProfileRequest getRequest =
        FreelancerGetProfileRequest.builder().withUserId(freelancerObj.getId().toString()).build();

    FreelancerGetProfileResponse getResponse =
        (FreelancerGetProfileResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, getRequest);

    assertEquals(setResponse.getStatusCode(), (HttpStatusCode.OK));
    assertEquals(getResponse.getStatusCode(), (HttpStatusCode.OK));
    assertEquals(getResponse.getFullName(), (setRequest.getFullName()));
    assertEquals(getResponse.getJobTitle(), (setRequest.getJobTitle()));
    assertEquals(getResponse.getCity(), (setRequest.getCity()));
    assertEquals(getResponse.getDescription(), (setRequest.getDescription()));
    assertEquals(getResponse.getBirthDate(), (setRequest.getBirthDate()));
    assertEquals(getResponse.getEmail(), (setRequest.getEmail()));
  }

  @Test
  void testFreelancerResume() {
    String resumeLink = "https://www.google.com";

    var freelancerObj = freelancerRepository.save(UsersTestUtils.generateRandomFreelancer());

    FreelancerSetResumeRequest setRequest =
        FreelancerSetResumeRequest.builder()
            .withUserId(freelancerObj.getId().toString())
            .withResumeLink(resumeLink)
            .build();

    FreelancerSetResumeResponse setResponse =
        (FreelancerSetResumeResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, setRequest);

    assertEquals(setResponse.getStatusCode(), (HttpStatusCode.OK));

    FreelancerGetResumeRequest getRequest =
        FreelancerGetResumeRequest.builder().withUserId(freelancerObj.getId().toString()).build();

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
        ClientGetPhotoRequest.builder().withUserId(clientObj.getId().toString()).build();

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
            .withUserId(clientObj.getId().toString())
            .withName("John Doe")
            .withCity("New York")
            .withIndustry("Software")
            .withDescription("I am a software engineer")
            .withEmployeeCount(10)
            .withEmail("test@gmail.com")
            .build();

    ClientSetProfileResponse setResponse =
        (ClientSetProfileResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, setRequest);

    ClientGetProfileRequest getRequest =
        ClientGetProfileRequest.builder().withUserId(clientObj.getId().toString()).build();

    ClientGetProfileResponse getResponse =
        (ClientGetProfileResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, getRequest);

    assertEquals(setResponse.getStatusCode(), (HttpStatusCode.OK));
    assertEquals(getResponse.getStatusCode(), (HttpStatusCode.OK));
    assertEquals(getResponse.getName(), (setRequest.getName()));
    assertEquals(getResponse.getCity(), (setRequest.getCity()));
    assertEquals(getResponse.getIndustry(), (setRequest.getIndustry()));
    assertEquals(getResponse.getDescription(), (setRequest.getDescription()));
    assertEquals(getResponse.getEmployeeCount(), (setRequest.getEmployeeCount()));
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

  @Test
  void testUpdateAchievement() {
    Freelancer freelancer = freelancerRepository.save(UsersTestUtils.createTestFreelancer());
    Achievement achievement = achievementRepository.save(UsersTestUtils.createTestAchievement());
    freelancer.getAchievements().add(achievement);
    freelancerRepository.save(freelancer);
    String updatedName = "New Achievement Name";
    String updatedAwardedBy = "New Awarded By";
    UpdateFreelancerAchievementRequest request =
        UpdateFreelancerAchievementRequest.builder()
            .withUserId(freelancer.getId().toString())
            .withAchievement_id(achievement.getId().toString())
            .withNew_achievement_name(updatedName)
            .withNew_awarded_by(updatedAwardedBy)
            .build();
    UpdateFreelancerAchievementResponse response =
        (UpdateFreelancerAchievementResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);
    Achievement expectedAchievement = UsersTestUtils.createTestAchievement();
    expectedAchievement.setAchievement_name(updatedName);
    expectedAchievement.setAwarded_by(updatedAwardedBy);
    expectedAchievement.setAward_date(achievement.getAward_date());
    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatusCode.OK, response.getStatusCode());
    freelancer = freelancerRepository.findById(freelancer.getId().toString()).get();
    Achievement updatedAchievement = freelancer.getAchievements().get(0);
    UsersTestUtils.assertAchievementEquals(expectedAchievement, updatedAchievement);
  }

  @Test
  void testUpdateEducation() {
    Freelancer freelancer = freelancerRepository.save(UsersTestUtils.createTestFreelancer());
    Education education = educationRepository.save(UsersTestUtils.createTestEducation());
    freelancer.getEducations().add(education);
    freelancerRepository.save(freelancer);

    String updatedSchoolName = "New School Name";
    String updatedDegree = "New Degree";
    UpdateFreelancerEducationRequest request =
        UpdateFreelancerEducationRequest.builder()
            .withUserId(freelancer.getId().toString())
            .withEducation_id(education.getId().toString())
            .withNew_school_name(updatedSchoolName)
            .withNew_degree(updatedDegree)
            .build();
    UpdateFreelancerEducationResponse response =
        (UpdateFreelancerEducationResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);
    Education expectedEducation = UsersTestUtils.createTestEducation();
    expectedEducation.setSchool_name(updatedSchoolName);
    expectedEducation.setDegree(updatedDegree);
    expectedEducation.setEducation_start_date(education.getEducation_start_date());
    expectedEducation.setEnd_date(education.getEnd_date());
    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatusCode.OK, response.getStatusCode());
    freelancer = freelancerRepository.findById(freelancer.getId().toString()).get();
    Education updatedEducation = freelancer.getEducations().get(0);
    UsersTestUtils.assertEducationEquals(expectedEducation, updatedEducation);
  }

  @Test
  void testUpdateExperience() {
    Freelancer freelancer = freelancerRepository.save(UsersTestUtils.createTestFreelancer());
    Experience experience = experienceRepository.save(UsersTestUtils.createTestExperience());
    freelancer.getExperiences().add(experience);
    freelancerRepository.save(freelancer);
    String updatedCompanyName = "New Company Name";
    String updatedJobTitle = "New Job Title";
    UpdateFreelancerExperienceRequest request =
        UpdateFreelancerExperienceRequest.builder()
            .withUserId(freelancer.getId().toString())
            .withExperience_id(experience.getId().toString())
            .withNew_company_name(updatedCompanyName)
            .withNew_job_title(updatedJobTitle)
            .build();
    UpdateFreelancerExperienceResponse response =
        (UpdateFreelancerExperienceResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatusCode.OK, response.getStatusCode());
    Experience expectedExperience = UsersTestUtils.createTestExperience();
    expectedExperience.setCompany_name(updatedCompanyName);
    expectedExperience.setJob_title(updatedJobTitle);
    expectedExperience.setEmployment_start(experience.getEmployment_start());
    expectedExperience.setEmployment_end(experience.getEmployment_end());
    freelancer = freelancerRepository.findById(freelancer.getId().toString()).get();
    Experience updatedExperience = freelancer.getExperiences().get(0);
    UsersTestUtils.assertExperienceEquals(expectedExperience, updatedExperience);
  }

  @Test
  void testRemoveAchievement() {
    Freelancer freelancer = freelancerRepository.save(UsersTestUtils.createTestFreelancer());
    Achievement achievement = achievementRepository.save(UsersTestUtils.createTestAchievement());
    freelancer.getAchievements().add(achievement);
    freelancerRepository.save(freelancer);

    RemoveFreelancerAchievementRequest request =
        RemoveFreelancerAchievementRequest.builder()
            .withUserId(freelancer.getId().toString())
            .withAchievementId(achievement.getId().toString())
            .build();
    RemoveFreelancerAchievementResponse response =
        (RemoveFreelancerAchievementResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatusCode.OK, response.getStatusCode());
    freelancer = freelancerRepository.findById(freelancer.getId().toString()).get();
    Assertions.assertTrue(freelancer.getAchievements().isEmpty());
  }

  @Test
  void testRemoveEducation() {
    Freelancer freelancer = freelancerRepository.save(UsersTestUtils.createTestFreelancer());
    Education education = educationRepository.save(UsersTestUtils.createTestEducation());
    freelancer.getEducations().add(education);
    freelancerRepository.save(freelancer);

    RemoveFreelancerEducationRequest request =
        RemoveFreelancerEducationRequest.builder()
            .withUserId(freelancer.getId().toString())
            .withEducation_id(education.getId().toString())
            .build();
    RemoveFreelancerEducationResponse response =
        (RemoveFreelancerEducationResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatusCode.OK, response.getStatusCode());
    freelancer = freelancerRepository.findById(freelancer.getId().toString()).get();
    Assertions.assertTrue(freelancer.getEducations().isEmpty());
  }

  @Test
  void testRemoveExperience() {
    Freelancer freelancer = freelancerRepository.save(UsersTestUtils.createTestFreelancer());
    Experience experience = experienceRepository.save(UsersTestUtils.createTestExperience());
    freelancer.getExperiences().add(experience);
    freelancerRepository.save(freelancer);

    RemoveFreelancerExperienceRequest request =
        RemoveFreelancerExperienceRequest.builder()
            .withUserId(freelancer.getId().toString())
            .withExperience_id(experience.getId().toString())
            .build();
    RemoveFreelancerExperienceResponse response =
        (RemoveFreelancerExperienceResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatusCode.OK, response.getStatusCode());
    freelancer = freelancerRepository.findById(freelancer.getId().toString()).get();
    Assertions.assertTrue(freelancer.getExperiences().isEmpty());
  }

  @Test
  void testRemoveSkill() {
    Freelancer freelancer = freelancerRepository.save(UsersTestUtils.createTestFreelancer());
    String skill = "Java";
    freelancer.getSkills().add(skill);
    freelancerRepository.save(freelancer);

    RemoveFreelancerSkillRequest request =
        RemoveFreelancerSkillRequest.builder()
            .withUserId(freelancer.getId().toString())
            .withSkillToRemove(skill)
            .build();
    RemoveFreelancerSkillResponse response =
        (RemoveFreelancerSkillResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatusCode.OK, response.getStatusCode());
    freelancer = freelancerRepository.findById(freelancer.getId().toString()).get();
    Assertions.assertTrue(freelancer.getSkills().isEmpty());
  }

  @Test
  void testRemoveLanguage() {
    Freelancer freelancer = freelancerRepository.save(UsersTestUtils.createTestFreelancer());
    String language = "English";
    freelancer.getLanguages().add(language);
    freelancerRepository.save(freelancer);

    RemoveFreelancerLanguageRequest request =
        RemoveFreelancerLanguageRequest.builder()
            .withUserId(freelancer.getId().toString())
            .withLanguageToRemove(language)
            .build();
    RemoveFreelancerLanguageResponse response =
        (RemoveFreelancerLanguageResponse)
            template.convertSendAndReceive(ServiceQueueNames.USERS, request);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(HttpStatusCode.OK, response.getStatusCode());
    freelancer = freelancerRepository.findById(freelancer.getId().toString()).get();
    Assertions.assertTrue(freelancer.getLanguages().isEmpty());
  }
}
