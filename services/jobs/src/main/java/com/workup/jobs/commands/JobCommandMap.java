package com.workup.jobs.commands;

import com.workup.jobs.repositories.JobRepository;
import com.workup.jobs.repositories.ProposalRepository;
import com.workup.shared.commands.CommandMap;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.CommandResponse;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobCommandMap
    extends CommandMap<JobCommand<? extends CommandRequest, ? extends CommandResponse>> {

  @Autowired public JobRepository jobRepository;

  @Autowired public ProposalRepository proposalRepository;

  @Autowired public AmqpTemplate rabbitTemplate;

  public void registerCommands() {
    commands.put("CreateJob", CreateJobCommand.class);
    commands.put("CreateProposal", CreateProposalCommand.class);
    commands.put("GetJobById", GetJobByIdCommand.class);
    commands.put("SearchJobs", SearchJobsCommand.class);
    commands.put("GetMyJobs", GetMyJobsCommand.class);
    commands.put("AcceptProposal", AcceptProposalCommand.class);
    commands.put("GetProposalsByJobId", GetProposalsByJobIdCommand.class);
    commands.put("GetMyProposals", GetMyProposalsCommand.class);
  }

  @Override
  public void setupCommand(
      JobCommand<? extends CommandRequest, ? extends CommandResponse> command) {
    command.setJobRepository(jobRepository);
    command.setProposalRepository(proposalRepository);
    command.setRabbitTemplate(rabbitTemplate);
  }
}
