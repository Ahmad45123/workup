package com.workup.jobs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workup.jobs.commands.CreateJobCommand;
import com.workup.jobs.commands.CreateProposalCommand;
import com.workup.shared.commands.jobs.proposals.requests.CreateProposalRequest;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workup.jobs.commands.JobCommandMap;
import com.workup.shared.commands.jobs.requests.CreateJobRequest;

@Service
@RabbitListener(queues = "jobsqueue")
public class RabbitMQListener {

    @Autowired
    public JobCommandMap commandMap;

    @RabbitHandler
    public void receive(CreateJobRequest in) throws Exception {
        ((CreateJobCommand) commandMap.getCommand("CreateJob")).Run(in);
    }

    @RabbitHandler
    public void receive(CreateProposalRequest in) throws Exception{
        ((CreateProposalCommand)commandMap.getCommand("CreateProposal")).Run(in);
    }
}
