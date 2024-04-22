package com.workup.jobs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workup.jobs.commands.*;
import com.workup.shared.commands.jobs.proposals.requests.AcceptProposalRequest;
import com.workup.shared.commands.jobs.proposals.requests.CreateProposalRequest;
import com.workup.shared.commands.jobs.proposals.responses.AcceptProposalResponse;
import com.workup.shared.commands.jobs.proposals.responses.CreateProposalResponse;
import com.workup.shared.commands.jobs.requests.CreateJobRequest;
import com.workup.shared.commands.jobs.requests.GetJobByIdRequest;
import com.workup.shared.commands.jobs.requests.GetMyJobsRequest;
import com.workup.shared.commands.jobs.requests.SearchJobsRequest;
import com.workup.shared.commands.jobs.responses.CreateJobResponse;
import com.workup.shared.commands.jobs.responses.GetJobByIdResponse;
import com.workup.shared.commands.jobs.responses.GetMyJobsResponse;
import com.workup.shared.commands.jobs.responses.SearchJobsResponse;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "jobsqueue")
public class RabbitMQListener {

  @Autowired
  public JobCommandMap commandMap;

  @RabbitHandler
  public CreateJobResponse receive(CreateJobRequest in) throws Exception {
    CreateJobResponse response =
      ((CreateJobCommand) commandMap.getCommand("CreateJob")).Run(in);
    return response;
  }

  @RabbitHandler
  public CreateProposalResponse receive(CreateProposalRequest in) throws Exception {
    CreateProposalResponse response =
      ((CreateProposalCommand) commandMap.getCommand("CreateProposal")).Run(in);
    return response;
  }

  @RabbitHandler
  public GetJobByIdResponse receive(GetJobByIdRequest request) throws Exception {
    GetJobByIdResponse response =
      ((GetJobByIdCommand) commandMap.getCommand("GetJobById")).Run(request);
    return response;
  }

  @RabbitHandler
  public SearchJobsResponse receive(SearchJobsRequest request) throws Exception {
    SearchJobsResponse response =
      ((SearchJobsCommand) commandMap.getCommand("SearchJobs")).Run(request);
    return response;
  }

  @RabbitHandler
  public GetMyJobsResponse receive(GetMyJobsRequest request) throws Exception {
    GetMyJobsResponse response =
      ((GetMyJobsCommand) commandMap.getCommand("GetMyJobs")).Run(request);
    return response;
  }

  @RabbitHandler
  public AcceptProposalResponse receive(AcceptProposalRequest request) throws Exception {
    AcceptProposalResponse response =
      ((AcceptProposalCommand) commandMap.getCommand("AcceptProposal")).Run(request);
    return response;
  }
}
