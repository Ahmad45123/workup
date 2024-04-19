package com.workup.jobs.commands;

import com.workup.jobs.repositories.JobRepository;
import com.workup.jobs.repositories.ProposalRepository;
import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.CommandResponse;

public abstract class JobCommand<T extends CommandRequest, Q extends CommandResponse> implements Command<T, Q>{
     JobRepository jobRepository;
     ProposalRepository proposalRepository;

    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public void setProposalRepository(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }
}
