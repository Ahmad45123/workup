package com.workup.jobs;

import com.workup.shared.commands.jobs.proposals.CreateProposalRequest;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workup.jobs.commands.CommandMap;
import com.workup.shared.commands.jobs.CreateJobRequest;

@Service
@RabbitListener(queues = "jobsqueue")
public class RabbitMQListener {

    @Autowired
    public CommandMap commandMap;

    @RabbitHandler
    public void receive(CreateJobRequest in) throws Exception {
        commandMap.GetCommand("CreateJob").Run(in);
    }

    @RabbitHandler
    public void receive(CreateProposalRequest in) {
        System.out.println(" [x] Int Received '" + in.coverLetter + "'");
    }
}
