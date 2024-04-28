package com.workup.jobs.commands;

import com.workup.jobs.models.Job;
import com.workup.shared.commands.jobs.requests.GetJobByIdRequest;
import com.workup.shared.commands.jobs.responses.GetJobByIdResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.util.Optional;
import java.util.UUID;

public class GetJobByIdCommand extends JobCommand<GetJobByIdRequest, GetJobByIdResponse> {

  @Override
  public GetJobByIdResponse Run(GetJobByIdRequest request) {
    try {
      UUID jobId = UUID.fromString(request.getJobId());

      Optional<Job> job = jobRepository.findById(jobId);
      if (job.isPresent()) {
        return GetJobByIdResponse.builder()
            .withId(job.get().getId().toString())
            .withTitle(job.get().getTitle())
            .withDescription(job.get().getDescription())
            .withLocation(job.get().getLocation())
            .withSkills(job.get().getSkills())
            .withExperience(job.get().getExperienceLevel())
            .withClientId(job.get().getClientId())
            .withIsActive(job.get().isActive())
            .withBudget(job.get().getBudget())
            .withCreatedAt(job.get().getCreatedAt())
            .withModifiedAt(job.get().getUpdatedAt())
            .withStatusCode(HttpStatusCode.OK)
            .build();
      } else {
        return GetJobByIdResponse.builder()
            .withStatusCode(HttpStatusCode.NOT_FOUND)
            .withErrorMessage("Job not found")
            .build();
      }
    } catch (Exception e) {
      return GetJobByIdResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage("An error occurred while fetching job")
          .build();
    }
  }
}
