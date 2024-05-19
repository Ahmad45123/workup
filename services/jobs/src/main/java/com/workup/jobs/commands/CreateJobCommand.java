package com.workup.jobs.commands;

import com.workup.jobs.models.Job;
import com.workup.shared.commands.jobs.requests.CreateJobRequest;
import com.workup.shared.commands.jobs.responses.CreateJobResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.Date;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateJobCommand extends JobCommand<CreateJobRequest, CreateJobResponse> {
  private static final Logger logger = LogManager.getLogger(CreateJobCommand.class);

  @Override
  public CreateJobResponse Run(CreateJobRequest request) {
    logger.info("[x] Saving job '" + request.getTitle() + "'");
    try {
      Job job =
          Job.builder()
              .withId(UUID.randomUUID())
              .withTitle(request.getTitle())
              .withDescription(request.getDescription())
              .withLocation(request.getLocation())
              .withBudget(request.getBudget())
              .withClientId(request.getUserId())
              .withSkills(request.getSkills())
              .withIsActive(true)
              .withExperienceLevel(request.getExperience())
              .withCreatedAt(new Date())
              .withUpdatedAt(new Date())
              .build();
      Job savedJob = jobRepository.save(job);
      logger.info("[x] Job saved with id: " + savedJob.getId());
      return CreateJobResponse.builder()
          .withStatusCode(HttpStatusCode.CREATED)
          .withJobId(savedJob.getId().toString())
          .build();
    } catch (Exception e) {
      logger.error("[x] An error occurred while saving job" + e.getMessage());
      return CreateJobResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage("An error occurred while saving job")
          .withJobId(null)
          .build();
    }
  }
}
