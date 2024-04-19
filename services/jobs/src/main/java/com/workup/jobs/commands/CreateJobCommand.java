package com.workup.jobs.commands;

import java.util.Date;
import java.util.UUID;

import com.workup.jobs.models.Job;
import com.workup.shared.commands.jobs.requests.CreateJobRequest;
import com.workup.shared.commands.jobs.responses.CreateJobResponse;
import com.workup.shared.enums.HttpStatusCode;;

public class CreateJobCommand extends JobCommand<CreateJobRequest, CreateJobResponse> {    

    @Override
    public CreateJobResponse Run(CreateJobRequest request) {
        Job job = Job.builder()
                .withId(UUID.randomUUID())
                .withTitle(request.getTitle())
                .withDescription(request.getDescription())
                .withLocation(request.getLocation())
                .withBudget(request.getBudget())
                .withClientId(request.getClientId())
                .withSkills(request.getSkills())
                .withIsActive(true)
                .withExperienceLevel(request.getExperience())
                .withCreatedAt(new Date())
                .withUpdatedAt(new Date())
                .build();
        try{
            Job savedJob = jobRepository.save(job);
            System.out.println(" [x] Saved Job '" + savedJob.getTitle()) ;
            return CreateJobResponse.builder()
                    .withStatusCode(HttpStatusCode.CREATED)
                    .withJobId(savedJob.getId().toString())
                    .build();
        }catch(Exception e){
            e.printStackTrace();
            return CreateJobResponse.builder()
                    .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
                    .withErrorMessage("An error occurred while saving job")
                    .withJobId(null)
                    .build();
        }
   
    }
    
}
