package com.workup.jobs;

import static org.junit.Assert.*;

import com.workup.jobs.models.Job;
import com.workup.jobs.models.Milestone;
import com.workup.jobs.models.Proposal;
import com.workup.jobs.repositories.JobRepository;
import com.workup.jobs.repositories.ProposalRepository;
import com.workup.shared.commands.jobs.proposals.JobDuration;
import com.workup.shared.commands.jobs.proposals.ProposalAttachment;
import com.workup.shared.commands.jobs.proposals.ProposalMilestone;
import com.workup.shared.commands.jobs.proposals.ProposalStatus;
import com.workup.shared.commands.jobs.proposals.requests.AcceptProposalRequest;
import com.workup.shared.commands.jobs.proposals.requests.CreateProposalRequest;
import com.workup.shared.commands.jobs.proposals.responses.AcceptProposalResponse;
import com.workup.shared.commands.jobs.proposals.responses.CreateProposalResponse;
import com.workup.shared.commands.jobs.requests.CreateJobRequest;
import com.workup.shared.commands.jobs.requests.SearchJobsRequest;
import com.workup.shared.commands.jobs.responses.CreateJobResponse;
import com.workup.shared.commands.jobs.responses.SearchJobsResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
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
class JobsApplicationTests {

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
    registry.add("spring.cassandra.contact-points", JobsApplicationTests::GetCassandraContactPoint);

    registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
    registry.add("spring.rabbitmq.port", rabbitMQContainer::getFirstMappedPort);
    registry.add("spring.rabbitmq.username", rabbitMQContainer::getAdminUsername);
    registry.add("spring.rabbitmq.password", rabbitMQContainer::getAdminPassword);
  }

  private static final String CLIENT_ONE_ID = "123";
  private static final String CLIENT_TWO_ID = "456";
  private static final String FREELANCER_ONE_ID = "789";

  @Autowired AmqpTemplate template;
  @Autowired JobRepository jobRepository;
  @Autowired ProposalRepository proposalRepository;

  @BeforeEach
  void clearAll() {
    jobRepository.deleteAll();
    proposalRepository.deleteAll();
  }

  @Test
  void testCreateJob() {
    CreateJobRequest createJobRequest =
        CreateJobRequest.builder()
            .withTitle("Convert HTML Template to React 3")
            .withDescription(
                "I have an HTML template that I have purchased and own the rights to. I would like"
                    + " it converted into a React application.")
            .withSkills(new String[] {"HTML", "CSS", "JavaScript", "React"})
            .withUserId(CLIENT_ONE_ID)
            .build();

    CreateJobResponse response =
        (CreateJobResponse) template.convertSendAndReceive("jobsqueue", createJobRequest);

    assertNotNull(response);
    assertTrue(response.getStatusCode() == HttpStatusCode.CREATED);

    jobRepository
        .findById(UUID.fromString(response.getJobId()))
        .ifPresentOrElse(
            job -> assertTrue(job.getTitle().equals(createJobRequest.getTitle())),
            () -> new RuntimeException("Job not found"));
  }

  /**
   * Creates a proposal for a given a job ID.
   *
   * @throws ParseException
   */
  @Test
  void testCreateProposal() throws ParseException {
    Job job =
        jobRepository.save(
            Job.builder()
                .withId(UUID.randomUUID())
                .withTitle("Convert HTML Template to React 3")
                .withDescription(
                    "I have an HTML template that I have purchased and own the rights to. I would"
                        + " like it converted into a React application.")
                .withSkills(new String[] {"HTML", "CSS", "JavaScript", "React"})
                .withClientId(CLIENT_ONE_ID)
                .build());

    List<ProposalMilestone> milestones = new ArrayList<>();
    milestones.add(
        ProposalMilestone.builder()
            .withDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01"))
            .withDescription("First milestone")
            .withAmount(4500)
            .build());
    List<ProposalAttachment> attachments = new ArrayList<>();
    attachments.add(
        ProposalAttachment.builder()
            .withName("attach")
            .withUrl("https://picsum.photos/200/300")
            .build());
    CreateProposalRequest request =
        CreateProposalRequest.builder()
            .withJobId(job.getId().toString())
            .withAttachments(attachments)
            .withUserId(FREELANCER_ONE_ID)
            .withJobDuration(JobDuration.THREE_TO_SIX_MONTHS)
            .withCoverLetter("Please Hire me :)!")
            .withMilestones(milestones)
            .build();
    CreateProposalResponse response =
        (CreateProposalResponse) template.convertSendAndReceive("jobsqueue", request);

    assertTrue(response.getStatusCode() == HttpStatusCode.CREATED);
  }

  @Test
  void testAcceptProposalInactiveJob() throws ParseException {
    Job job =
        jobRepository.save(
            Job.builder().withId(UUID.randomUUID()).withClientId(CLIENT_ONE_ID).build());
    Proposal.ProposalPrimaryKey proposalPrimaryKey =
        Proposal.ProposalPrimaryKey.builder()
            .withJobId(job.getId().toString())
            .withId(UUID.randomUUID())
            .build();

    Milestone fakeMilestone =
        Milestone.builder()
            .withDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01"))
            .withDescription("First milestone")
            .withAmount(4500)
            .build();
    ArrayList<Milestone> milestoneList = new ArrayList<Milestone>();
    milestoneList.add(fakeMilestone);

    Proposal proposal =
        proposalRepository.save(
            Proposal.builder()
                .withPrimaryKey(proposalPrimaryKey)
                .withMilestones(milestoneList)
                .withStatus(ProposalStatus.PENDING)
                .build());

    AcceptProposalRequest request =
        AcceptProposalRequest.builder()
            .withJobId(job.getId().toString())
            .withProposalId(proposal.getPrimaryKey().getId().toString())
            .withUserId(CLIENT_ONE_ID)
            .build();

    AcceptProposalResponse response =
        (AcceptProposalResponse) template.convertSendAndReceive("jobsqueue", request);

    assertTrue(response.getStatusCode() == HttpStatusCode.BAD_REQUEST);
    assertTrue(
        proposalRepository.findById(proposal.getPrimaryKey()).get().getStatus()
            == ProposalStatus.PENDING);
  }

  @Test
  void testAcceptProposal() throws ParseException {
    Job job =
        jobRepository.save(
            Job.builder()
                .withId(UUID.randomUUID())
                .withIsActive(true)
                .withClientId(CLIENT_ONE_ID)
                .build());
    Proposal.ProposalPrimaryKey proposalPrimaryKey =
        Proposal.ProposalPrimaryKey.builder()
            .withJobId(job.getId().toString())
            .withId(UUID.randomUUID())
            .build();

    Milestone fakeMilestone =
        Milestone.builder()
            .withDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01"))
            .withDescription("First milestone")
            .withAmount(4500)
            .build();
    ArrayList<Milestone> milestoneList = new ArrayList<Milestone>();
    milestoneList.add(fakeMilestone);

    Proposal proposal =
        proposalRepository.save(
            Proposal.builder()
                .withPrimaryKey(proposalPrimaryKey)
                .withMilestones(milestoneList)
                .withStatus(ProposalStatus.PENDING)
                .build());

    AcceptProposalRequest request =
        AcceptProposalRequest.builder()
            .withJobId(job.getId().toString())
            .withProposalId(proposal.getPrimaryKey().getId().toString())
            .withUserId(CLIENT_ONE_ID)
            .build();

    String fakeContractId = UUID.randomUUID().toString();
    ContractsMockingListener.contractIdToBeReturned = fakeContractId;
    ContractsMockingListener.statusCodeToBeReturned = HttpStatusCode.CREATED;

    AcceptProposalResponse response =
        (AcceptProposalResponse) template.convertSendAndReceive("jobsqueue", request);

    assertTrue(response.getStatusCode() == HttpStatusCode.OK);
    assertTrue(
        proposalRepository.findById(proposal.getPrimaryKey()).get().getStatus()
            == ProposalStatus.ACCEPTED);
    assertEquals(fakeContractId, response.getContractId());
    assertTrue(!jobRepository.findById(job.getId()).get().isActive());
  }

  @Test
  void testAcceptProposalWithWrongUser() throws ParseException {
    Job job =
        jobRepository.save(
            Job.builder()
                .withIsActive(true)
                .withId(UUID.randomUUID())
                .withClientId(CLIENT_ONE_ID)
                .build());
    Proposal.ProposalPrimaryKey proposalPrimaryKey =
        Proposal.ProposalPrimaryKey.builder()
            .withJobId(job.getId().toString())
            .withId(UUID.randomUUID())
            .build();
    Milestone fakeMilestone =
        Milestone.builder()
            .withDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01"))
            .withDescription("First milestone")
            .withAmount(4500)
            .build();
    ArrayList<Milestone> milestoneList = new ArrayList<Milestone>();
    milestoneList.add(fakeMilestone);
    Proposal proposal =
        proposalRepository.save(
            Proposal.builder()
                .withPrimaryKey(proposalPrimaryKey)
                .withMilestones(milestoneList)
                .withStatus(ProposalStatus.PENDING)
                .build());

    AcceptProposalRequest request =
        AcceptProposalRequest.builder()
            .withJobId(job.getId().toString())
            .withProposalId(proposal.getPrimaryKey().getId().toString())
            .withUserId(CLIENT_TWO_ID)
            .build();

    AcceptProposalResponse response =
        (AcceptProposalResponse) template.convertSendAndReceive("jobsqueue", request);

    assertTrue(response.getStatusCode() == HttpStatusCode.UNAUTHORIZED);
    assertTrue(
        proposalRepository.findById(proposal.getPrimaryKey()).get().getStatus()
            == ProposalStatus.PENDING);
  }

  @Test
  void testSearchJob() {
    Job job1 =
        Job.builder()
            .withTitle("HTML Developer")
            .withDescription("HTML")
            .withId(UUID.randomUUID())
            .build();
    Job job2 =
        Job.builder()
            .withTitle("HTML")
            .withId(UUID.randomUUID())
            .withDescription("Developer")
            .build();
    Job job3 =
        Job.builder()
            .withTitle("Ahmed")
            .withSkills(new String[] {"Developer"})
            .withId(UUID.randomUUID())
            .build();
    Job job4 = Job.builder().withTitle("Testdata").withId(UUID.randomUUID()).build();

    jobRepository.save(job1);
    jobRepository.save(job2);
    jobRepository.save(job3);
    jobRepository.save(job4);

    SearchJobsRequest searchJobsCommand =
        SearchJobsRequest.builder().withPageLimit(2).withQuery("Developer").build();
    SearchJobsResponse resp =
        (SearchJobsResponse) template.convertSendAndReceive("jobsqueue", searchJobsCommand);

    HashSet<String> jobIds = new HashSet<>();
    jobIds.add(job1.getId().toString());
    jobIds.add(job2.getId().toString());
    jobIds.add(job3.getId().toString());

    assertEquals(HttpStatusCode.OK, resp.getStatusCode());
    assertEquals(2, resp.getJobs().length);
    assertTrue(jobIds.contains(resp.getJobs()[0].getId()));
    jobIds.remove(resp.getJobs()[0].getId());
    assertTrue(jobIds.contains(resp.getJobs()[1].getId()));
    jobIds.remove(resp.getJobs()[1].getId());

    searchJobsCommand =
        SearchJobsRequest.builder()
            .withPageLimit(2)
            .withQuery("Developer")
            .withPagingState(resp.getPagingState())
            .build();
    resp = (SearchJobsResponse) template.convertSendAndReceive("jobsqueue", searchJobsCommand);

    assertEquals(HttpStatusCode.OK, resp.getStatusCode());
    assertEquals(1, resp.getJobs().length);
    assertTrue(jobIds.contains(resp.getJobs()[0].getId()));
    jobIds.remove(resp.getJobs()[0].getId());

    assertTrue(jobIds.isEmpty());
  }
}
