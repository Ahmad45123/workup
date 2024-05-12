package com.workup.jobs;

import com.workup.jobs.commands.AcceptProposalCommand;
import com.workup.jobs.commands.CreateJobCommand;
import com.workup.jobs.commands.CreateProposalCommand;
import com.workup.jobs.commands.GetJobByIdCommand;
import com.workup.jobs.commands.GetMyJobsCommand;
import com.workup.jobs.commands.GetMyProposalsCommand;
import com.workup.jobs.commands.GetProposalsByJobIdCommand;
import com.workup.jobs.commands.JobCommandMap;
import com.workup.jobs.commands.SearchJobsCommand;
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

import java.util.concurrent.CompletableFuture;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = ServiceQueueNames.JOBS)
public class RabbitMQListener {

  @Autowired public JobCommandMap commandMap;

  @RabbitHandler
  @Async
  public CompletableFuture<CreateJobResponse> receive(CreateJobRequest in) throws Exception {
    CreateJobResponse response = ((CreateJobCommand) commandMap.getCommand("CreateJob")).Run(in);
    return CompletableFuture.completedFuture(response);
  }

  @RabbitHandler
  @Async
  public CompletableFuture<CreateProposalResponse> receive(CreateProposalRequest in) throws Exception {
    CreateProposalResponse response =
        ((CreateProposalCommand) commandMap.getCommand("CreateProposal")).Run(in);
    return CompletableFuture.completedFuture(response);
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetJobByIdResponse> receive(GetJobByIdRequest request) throws Exception {
    GetJobByIdResponse response =
        ((GetJobByIdCommand) commandMap.getCommand("GetJobById")).Run(request);
    return CompletableFuture.completedFuture(response);
  }

  @RabbitHandler
  @Async
  public CompletableFuture<SearchJobsResponse> receive(SearchJobsRequest request) throws Exception {
    SearchJobsResponse response =
        ((SearchJobsCommand) commandMap.getCommand("SearchJobs")).Run(request);
    return CompletableFuture.completedFuture(response);
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetMyJobsResponse> receive(GetMyJobsRequest request) throws Exception {
    GetMyJobsResponse response =
        ((GetMyJobsCommand) commandMap.getCommand("GetMyJobs")).Run(request);
    return CompletableFuture.completedFuture(response);
  }

  @RabbitHandler
  @Async
  public CompletableFuture<AcceptProposalResponse> receive(AcceptProposalRequest request) throws Exception {
    AcceptProposalResponse response =
        ((AcceptProposalCommand) commandMap.getCommand("AcceptProposal")).Run(request);
    return CompletableFuture.completedFuture(response);
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetProposalsByJobIdResponse> receive(GetProposalsByJobIdRequest request) throws Exception {
    GetProposalsByJobIdResponse response =
        ((GetProposalsByJobIdCommand) commandMap.getCommand("GetProposalsByJobId")).Run(request);
    return CompletableFuture.completedFuture(response);
  }

  @RabbitHandler
  @Async
  public CompletableFuture<GetMyProposalsResponse> receive(GetMyProposalsRequest request) throws Exception {
    GetMyProposalsResponse response =
        ((GetMyProposalsCommand) commandMap.getCommand("GetMyProposals")).Run(request);
    return CompletableFuture.completedFuture(response);
  }
}
