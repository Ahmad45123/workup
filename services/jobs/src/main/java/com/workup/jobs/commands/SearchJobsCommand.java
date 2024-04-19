package com.workup.jobs.commands;

import java.util.List;

import com.workup.jobs.models.Job;
import com.workup.shared.commands.jobs.JobListingItem;
import com.workup.shared.commands.jobs.requests.SearchJobsRequest;
import com.workup.shared.commands.jobs.responses.SearchJobsResponse;

public class SearchJobsCommand extends JobCommand<SearchJobsRequest, SearchJobsResponse>{

    @Override
    public SearchJobsResponse Run(SearchJobsRequest request) {
        List<Job> result = jobRepository.searchForJob(request.getQuery());
        return SearchJobsResponse.builder().withJobs(result.stream().map((Job job) -> {
            return JobListingItem.builder()
                .withDescription(job.getDescription())
                .withExperience(job.getExperienceLevel())
                .withId(job.getId().toString())
                .withSkills(job.getSkills())
                .withTitle(job.getTitle())
                .build();
        }).toArray(JobListingItem[]::new)).build();
    }
    
}
