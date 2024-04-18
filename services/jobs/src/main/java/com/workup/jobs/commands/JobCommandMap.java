package com.workup.jobs.commands;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.workup.jobs.repositories.JobRepository;
import com.workup.jobs.repositories.ProposalRepository;

import org.springframework.stereotype.Component;

import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandMap;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.CommandResponse;

@Component
public class JobCommandMap extends CommandMap<JobCommand<? extends CommandRequest, ? extends CommandResponse>> {
    @Autowired
    JobRepository jobRepository;
    @Autowired 
    ProposalRepository proposalRepository;


    public void registerCommands() {
        commands.put("CreateJob", CreateJobCommand.class);
        commands.put("CreateProposal", CreateProposalCommand.class);
    }

    @Override
    public void setupCommand(JobCommand<? extends CommandRequest, ? extends CommandResponse> command) {
        command.setJobRepository(jobRepository);
        command.setProposalRepository(proposalRepository);
    }
}
