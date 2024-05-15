package com.workup.jobs.commands;

import com.workup.jobs.models.Job;
import com.workup.shared.commands.jobs.JobListingItem;
import com.workup.shared.commands.jobs.requests.GetMyJobsRequest;
import com.workup.shared.commands.jobs.responses.GetMyJobsResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetMyJobsCommand extends JobCommand<GetMyJobsRequest, GetMyJobsResponse> {
  private static final Logger logger = LogManager.getLogger(GetMyJobsCommand.class);

  @Override
  public GetMyJobsResponse Run(GetMyJobsRequest request) {

    try {
      String clientId = request.getUserId();
      List<Job> jobs = jobRepository.getJobsByClientId(clientId);
      logger.info("[x] Jobs fetched for client: " + clientId);
      return GetMyJobsResponse.builder()
          .withJobs(
              jobs.stream()
                  .map(
                      job ->
                          JobListingItem.builder()
                              .withId(job.getId().toString())
                              .withTitle(job.getTitle())
                              .withDescription(job.getDescription())
                              .withExperience(job.getExperienceLevel())
                              .withSkills(job.getSkills())
                              .build())
                  .toArray(JobListingItem[]::new))
          .withStatusCode(HttpStatusCode.OK)
          .build();
    } catch (Exception e) {
      logger.error("[x] An error occurred while fetching jobs", e.getMessage());
      return GetMyJobsResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage("An error occurred while fetching jobs")
          .build();
    }
  }
}
