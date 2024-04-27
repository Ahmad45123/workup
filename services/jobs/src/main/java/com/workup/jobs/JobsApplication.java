package com.workup.jobs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workup.shared.commands.jobs.proposals.JobDuration;
import com.workup.shared.commands.jobs.proposals.ProposalAttachment;
import com.workup.shared.commands.jobs.proposals.ProposalMilestone;
import com.workup.shared.commands.jobs.proposals.requests.AcceptProposalRequest;
import com.workup.shared.commands.jobs.proposals.requests.CreateProposalRequest;
import com.workup.shared.commands.jobs.proposals.responses.AcceptProposalResponse;
import com.workup.shared.commands.jobs.proposals.responses.CreateProposalResponse;
import com.workup.shared.commands.jobs.requests.CreateJobRequest;
import com.workup.shared.commands.jobs.requests.SearchJobsRequest;
import com.workup.shared.commands.jobs.responses.CreateJobResponse;
import com.workup.shared.commands.jobs.responses.SearchJobsResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JobsApplication {

  private static final String CLIENT_ONE_ID = "123";
  private static final String CLIENT_TWO_ID = "456";
  private static final String FREELANCER_ONE_ID = "789";
  private static final ObjectMapper mapper = new ObjectMapper();

  public static void main(String[] args) {
    SpringApplication.run(JobsApplication.class, args);
  }

  @Bean
  public ApplicationRunner runner(AmqpTemplate template) {
    return args -> {
      System.out.println("[ ] Testing create job.");
      List<String> jobIds = testCreateJob(template);
      System.out.println("[X] Testing create job done!");

      System.out.println("[ ] Testing search for job.");
      testSearchJob(template);
      System.out.println("[X] Testing search for job done!");

      System.out.println("[ ] Testing create proposal.");
      String proposalId = testCreateProposal(template, jobIds.getFirst());
      System.out.println("[X] Testing create proposal done!");

      System.out.println("[ ] Testing accept proposal.");
      testAcceptProposal(template, jobIds.getFirst(), proposalId);
      System.out.println("[X] Testing accept proposal done!");
    };
  }

  /**
   * Creates two jobs, one with user id 123, while the other with user id 456.
   *
   * @param template the RabbitMQ template.
   * @return a list of the ids of created jobs.
   */
  private List<String> testCreateJob(AmqpTemplate template)
    throws JsonProcessingException {
    ArrayList<String> jobIds = new ArrayList<>();
    CreateJobRequest createJobRequest = CreateJobRequest
      .builder()
      .withTitle("Convert HTML Template to React 3")
      .withDescription(
        "I have an HTML template that I have purchased and own the rights to. I would like it converted into a React application."
      )
      .withSkills(new String[] { "HTML", "CSS", "JavaScript", "React" })
      .withUserId(CLIENT_ONE_ID)
      .build();
    CreateJobResponse response = (CreateJobResponse) template.convertSendAndReceive(
      "jobsqueue",
      createJobRequest
    );
    jobIds.add(response.getJobId());
    System.out.println("First job response:");
    System.out.println(mapper.writeValueAsString(response));
    createJobRequest =
      CreateJobRequest
        .builder()
        .withTitle(
          "Create an interactive web platform where users can create profiles, view them as nodes in a graph"
        )
        .withDescription(
          "Create an interactive web platform where users can create couch profiles (how do you describe yourself as a couch), view them as nodes in a graph, and interact with others."
        )
        .withSkills(new String[] { "Nodejs", "Neo4j", "Javascript" })
        .withUserId(CLIENT_TWO_ID)
        .build();
    response =
      (CreateJobResponse) template.convertSendAndReceive("jobsqueue", createJobRequest);
    jobIds.add(response.getJobId());
    System.out.println("Second job response:");
    System.out.println(mapper.writeValueAsString(response));
    return jobIds;
  }

  private void testSearchJob(AmqpTemplate template) {
    SearchJobsRequest searchJobsCommand = SearchJobsRequest
      .builder()
      .withPageLimit(5)
      .withQuery("HTML")
      .build();
    SearchJobsResponse resp = (SearchJobsResponse) template.convertSendAndReceive(
      "jobsqueue",
      searchJobsCommand
    );

    System.out.println("Search results: " + resp.getJobs().length);
    for (int i = 0; i < resp.getJobs().length; i++) {
      System.out.println("Job: " + resp.getJobs()[i].getTitle());
    }
    System.out.println(resp.getPagingState());

    System.out.println("*****");

    searchJobsCommand =
      SearchJobsRequest
        .builder()
        .withPagingState(resp.getPagingState())
        .withPageLimit(5)
        .withQuery("HTML")
        .build();
    resp =
      (SearchJobsResponse) template.convertSendAndReceive("jobsqueue", searchJobsCommand);

    System.out.println("Search results 2: " + resp.getJobs().length);
    for (int i = 0; i < resp.getJobs().length; i++) {
      System.out.println("Job: " + resp.getJobs()[i].getTitle());
    }
  }

  /**
   * Creates a proposal for a given a job ID.
   *
   * @param template the RabbitMQ template.
   * @param jobId    the id of the job to which the proposal is created.
   * @return a string of the proposal ID.
   */
  private String testCreateProposal(AmqpTemplate template, String jobId)
    throws JsonProcessingException, ParseException {
    List<ProposalMilestone> milestones = new ArrayList<>();
    milestones.add(
      ProposalMilestone
        .builder()
        .withDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01"))
        .withDescription("First milestone")
        .withAmount(4500)
        .build()
    );
    List<ProposalAttachment> attachments = new ArrayList<>();
    attachments.add(
      ProposalAttachment
        .builder()
        .withName("attach")
        .withUrl("https://picsum.photos/200/300")
        .build()
    );
    CreateProposalRequest request = CreateProposalRequest
      .builder()
      .withJobId(jobId)
      .withAttachments(attachments)
      .withUserId(FREELANCER_ONE_ID)
      .withJobDuration(JobDuration.THREE_TO_SIX_MONTHS)
      .withCoverLetter("Please Hire me :)!")
      .withMilestones(milestones)
      .build();
    CreateProposalResponse response = (CreateProposalResponse) template.convertSendAndReceive(
      "jobsqueue",
      request
    );
    System.out.println("Create proposal response:");
    System.out.println(mapper.writeValueAsString(response));
    return response.getId();
  }

  /**
   * Accept a proposal for a given a job ID, with user id "789".
   *
   * @param template   the RabbitMQ template.
   * @param jobId      the id of the job.
   * @param proposalId the id of the proposal.
   * @return a string of the created initial contract ID.
   */
  private String testAcceptProposal(
    AmqpTemplate template,
    String jobId,
    String proposalId
  ) throws JsonProcessingException {
    AcceptProposalRequest request = AcceptProposalRequest
      .builder()
      .withJobId(jobId)
      .withProposalId(proposalId)
      .withUserId(CLIENT_ONE_ID)
      .build();
    AcceptProposalResponse response = (AcceptProposalResponse) template.convertSendAndReceive(
      "jobsqueue",
      request
    );
    System.out.println("Accept proposal response:");
    System.out.println(mapper.writeValueAsString(response));
    return response.getContractId();
  }

  @Bean
  public Queue myQueue() {
    return new Queue("jobsqueue");
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
