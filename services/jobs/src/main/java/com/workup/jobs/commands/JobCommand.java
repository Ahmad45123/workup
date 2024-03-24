package com.workup.jobs.commands;

import com.workup.jobs.repositories.JobRepository;
import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandRequest;

public abstract class JobCommand<T extends CommandRequest> implements Command<T>{
     JobRepository jobRepository;

  

    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
}
