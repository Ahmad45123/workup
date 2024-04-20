package com.workup.jobs.commands;

import java.util.UUID;

import com.workup.jobs.models.Job;
import com.workup.shared.commands.jobs.requests.CreateJobRequest;
import com.workup.shared.commands.jobs.responses.CreateJobResponse;;

public class CreateJobCommand extends JobCommand<CreateJobRequest, CreateJobResponse> {    

    @Override
    public CreateJobResponse Run(CreateJobRequest request) {
        Job job = Job.builder()
                .withId(UUID.randomUUID())
                .withTitle(request.getTitle())
                .withDescription(request.getDescription())
                .withExperienceLevel(request.getExperience())
                .build();
        try{
            Job savedJob = jobRepository.save(job);
            System.out.println(" [x] Saved Job '" + savedJob.getTitle()) ;
            return CreateJobResponse.builder()
                    .withSuccess(true)
                    .withJobId(savedJob.getId().toString())
                    .build();
        }catch(Exception e){
            e.printStackTrace();
            return CreateJobResponse.builder()
                    .withSuccess(false)
                    .withJobId(null)
                    .build();
        }
   
    }
    
}
