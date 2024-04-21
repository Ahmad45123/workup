package com.workup.jobs.commands;

import java.util.List;

import com.workup.jobs.models.Job;
import com.workup.shared.commands.jobs.JobListingItem;
import com.workup.shared.commands.jobs.requests.GetMyJobsRequest;
import com.workup.shared.commands.jobs.responses.GetMyJobsResponse;
import com.workup.shared.enums.HttpStatusCode;

public class GetMyJobsCommand extends JobCommand<GetMyJobsRequest, GetMyJobsResponse>{

    @Override
    public GetMyJobsResponse Run(GetMyJobsRequest request) {
        try {
        String clientId = request.getUserId();
            List<Job> jobs = jobRepository.getJobsByClientId(clientId);
            return GetMyJobsResponse.builder()
                .withJobs(jobs.stream().map(job -> JobListingItem.builder()
                    .withId(job.getId().toString())
                    .withTitle(job.getTitle())
                    .withDescription(job.getDescription())
                    .withExperience(job.getExperienceLevel())
                    .withSkills(job.getSkills())
                    .build()).toArray(JobListingItem[]::new))
                .withStatusCode(HttpStatusCode.OK)
                .build();
        }catch(Exception e){
            return GetMyJobsResponse.builder()
                .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
                .withErrorMessage("An error occurred while fetching jobs")
                .build();
        }
    }
}
