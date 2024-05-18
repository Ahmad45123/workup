package com.workup.jobs;

import com.workup.jobs.commands.JobCommandMap;
import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandRequest;
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
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.ServiceQueueNames;
import java.util.concurrent.CompletableFuture;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = ServiceQueueNames.JOBS, id = ServiceQueueNames.JOBS)
public class RabbitMQListener {

  @Autowired public JobCommandMap commandMap;

  @RabbitHandler
  @Async
  public CompletableFuture<CreateJobResponse> receive(CreateJobRequest in) throws Exception {
    try {
      CreateJobResponse resp =
          (CreateJobResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("CreateJob")).Run(in);
      return CompletableFuture.completedFuture(resp);
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          CreateJobResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<CreateProposalResponse> receive(CreateProposalRequest in)
      throws Exception {
    try {
      CreateProposalResponse response =
          (CreateProposalResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("CreateProposal")).Run(in);
      return CompletableFuture.completedFuture(response);
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          CreateProposalResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetJobByIdResponse> receive(GetJobByIdRequest request) throws Exception {
    try {
      GetJobByIdResponse response =
          (GetJobByIdResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("GetJobById")).Run(request);
      return CompletableFuture.completedFuture(response);
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetJobByIdResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<SearchJobsResponse> receive(SearchJobsRequest request) throws Exception {
    try {
      SearchJobsResponse response =
          (SearchJobsResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("SearchJobs")).Run(request);
      return CompletableFuture.completedFuture(response);
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          SearchJobsResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetMyJobsResponse> receive(GetMyJobsRequest request) throws Exception {
    try {
      GetMyJobsResponse response =
          (GetMyJobsResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("GetMyJobs")).Run(request);
      return CompletableFuture.completedFuture(response);
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetMyJobsResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<AcceptProposalResponse> receive(AcceptProposalRequest request)
      throws Exception {
    try {
      AcceptProposalResponse response =
          (AcceptProposalResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("AcceptProposal")).Run(request);
      return CompletableFuture.completedFuture(response);
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          AcceptProposalResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetProposalsByJobIdResponse> receive(GetProposalsByJobIdRequest request)
      throws Exception {
    try {
      GetProposalsByJobIdResponse response =
          (GetProposalsByJobIdResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("GetProposalsByJobId"))
                  .Run(request);
      return CompletableFuture.completedFuture(response);
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetProposalsByJobIdResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetMyProposalsResponse> receive(GetMyProposalsRequest request)
      throws Exception {
    try {
      GetMyProposalsResponse response =
          (GetMyProposalsResponse)
              ((Command<CommandRequest, ?>) commandMap.getCommand("GetMyProposals")).Run(request);
      return CompletableFuture.completedFuture(response);
    } catch (Exception ex) {
      return CompletableFuture.completedFuture(
          GetMyProposalsResponse.builder()
              .withErrorMessage("Not Implemented.")
              .withStatusCode(HttpStatusCode.NOT_IMPLEMENTED)
              .build());
    }
  }
}
