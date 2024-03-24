package com.workup.jobs.commands;

import java.util.UUID;

import com.workup.jobs.models.Job;
import com.workup.shared.commands.*;
import com.workup.shared.commands.jobs.requests.CreateJobRequest;;

public class CreateJobCommand extends JobCommand<CreateJobRequest> {    

    @Override
    public void Run(CreateJobRequest request) {
        Job job = Job.builder()
                .withId(UUID.randomUUID())
                .withTitle(request.getTitle())
                .withDescription(request.getDescription())
                .withExperienceLevel(request.getExperience())
                .build();
        try{
        Job savedJob = jobRepository.save(job);
        System.out.println(" [x] Saved Job '" + savedJob.getTitle()) ;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
