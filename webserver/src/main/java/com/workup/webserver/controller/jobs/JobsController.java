package com.workup.webserver.controller.jobs;

import com.workup.shared.commands.jobs.proposals.requests.AcceptProposalRequest;
import com.workup.shared.commands.jobs.proposals.requests.CreateProposalRequest;
import com.workup.shared.commands.jobs.proposals.requests.GetMyProposalsRequest;
import com.workup.shared.commands.jobs.proposals.requests.GetProposalsByJobIdRequest;
import com.workup.shared.commands.jobs.proposals.responses.AcceptProposalResponse;
import com.workup.shared.commands.jobs.proposals.responses.CreateProposalResponse;
import com.workup.shared.commands.jobs.proposals.responses.GetMyProposalsResponse;
import com.workup.shared.commands.jobs.proposals.responses.GetProposalsByJobIdResponse;
import com.workup.shared.commands.jobs.requests.CreateJobRequest;
import com.workup.shared.commands.jobs.requests.GetJobByIdRequest;
import com.workup.shared.commands.jobs.requests.GetMyJobsRequest;
import com.workup.shared.commands.jobs.requests.SearchJobsRequest;
import com.workup.shared.commands.jobs.responses.CreateJobResponse;
import com.workup.shared.commands.jobs.responses.GetJobByIdResponse;
import com.workup.shared.commands.jobs.responses.GetMyJobsResponse;
import com.workup.shared.commands.jobs.responses.SearchJobsResponse;
import com.workup.shared.enums.ServiceQueueNames;
import java.util.Optional;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobsController {
  @Autowired AmqpTemplate rabbitTemplate;

  static String userId = "123";

  @GetMapping("/{id}")
  public ResponseEntity<GetJobByIdResponse> getJobById(@PathVariable String id) {
    // send in queue
    GetJobByIdRequest request = GetJobByIdRequest.builder().withJobId(id).build();
    // TODO replace with userId  from token
    request.setUserId(userId);
    GetJobByIdResponse response =
        (GetJobByIdResponse) rabbitTemplate.convertSendAndReceive(ServiceQueueNames.JOBS, request);
    // return response entity with response and status code
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @PostMapping()
  public ResponseEntity<CreateJobResponse> createJob(@RequestBody CreateJobRequest request) {
    request.setUserId(userId);
    CreateJobResponse response =
        (CreateJobResponse) rabbitTemplate.convertSendAndReceive(ServiceQueueNames.JOBS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @GetMapping("/search")
  public ResponseEntity<SearchJobsResponse> searchForJobs(
      @RequestParam(name = "query") String query,
      @RequestParam(name = "pageLimit") int pageLimit,
      @RequestParam(name = "pagingState") Optional<String> pagingState) {
    SearchJobsRequest request =
        SearchJobsRequest.builder()
            .withQuery(query)
            .withPageLimit(pageLimit)
            .withPagingState(pagingState.isPresent() ? pagingState.get() : null)
            .build();
    SearchJobsResponse response =
        (SearchJobsResponse) rabbitTemplate.convertSendAndReceive(ServiceQueueNames.JOBS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @GetMapping("/me")
  public ResponseEntity<GetMyJobsResponse> getMyJobs() {
    GetMyJobsRequest request = GetMyJobsRequest.builder().withUserId(userId).build();
    GetMyJobsResponse response =
        (GetMyJobsResponse) rabbitTemplate.convertSendAndReceive(ServiceQueueNames.JOBS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @PostMapping("/{id}/proposals")
  public ResponseEntity<CreateProposalResponse> createProposal(
      @PathVariable String id, @RequestBody CreateProposalRequest request) {
    request.setUserId(userId);
    request.setJobId(id);
    CreateProposalResponse response =
        (CreateProposalResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.JOBS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @GetMapping("/{id}/proposals")
  public ResponseEntity<GetProposalsByJobIdResponse> getProposalsByJobId(@PathVariable String id) {
    GetProposalsByJobIdRequest request = GetProposalsByJobIdRequest.builder().withJobId(id).build();
    request.setUserId(userId);
    GetProposalsByJobIdResponse response =
        (GetProposalsByJobIdResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.JOBS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @GetMapping("/me/proposals")
  public ResponseEntity<GetMyProposalsResponse> getMyProposals() {
    GetMyProposalsRequest request = GetMyProposalsRequest.builder().build();
    request.setUserId(userId);
    GetMyProposalsResponse response =
        (GetMyProposalsResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.JOBS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }

  @GetMapping("/{jobId}/proposals/{proposalId}/accept")
  public ResponseEntity<AcceptProposalResponse> acceptProposal(
      @PathVariable(name = "jobId") String jobId,
      @PathVariable(name = "proposalId") String proposalId) {
    AcceptProposalRequest request =
        AcceptProposalRequest.builder().withJobId(jobId).withProposalId(proposalId).build();
    request.setUserId(userId);
    AcceptProposalResponse response =
        (AcceptProposalResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.JOBS, request);
    return ResponseEntity.status(response.getStatusCode().getValue()).body(response);
  }
}
