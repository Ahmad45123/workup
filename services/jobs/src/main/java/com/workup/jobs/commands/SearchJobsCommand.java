package com.workup.jobs.commands;

import com.workup.jobs.models.Job;
import com.workup.shared.commands.jobs.JobListingItem;
import com.workup.shared.commands.jobs.requests.SearchJobsRequest;
import com.workup.shared.commands.jobs.responses.SearchJobsResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.nio.ByteBuffer;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

public class SearchJobsCommand extends JobCommand<SearchJobsRequest, SearchJobsResponse> {

  @Override
  public SearchJobsResponse Run(SearchJobsRequest request) {
    try {
      CassandraPageRequest pageRequest;

      if (request.getPagingState() != null) {
        PageRequest pageReq = PageRequest.of(0, request.getPageLimit());
        ByteBuffer byteBuffer =
            com.datastax.oss.protocol.internal.util.Bytes.fromHexString(request.getPagingState());
        pageRequest = CassandraPageRequest.of(pageReq, byteBuffer);
      } else {
        pageRequest = CassandraPageRequest.of(0, request.getPageLimit());
      }

      Slice<Job> result = jobRepository.searchForJob("%" + request.getQuery() + "%", pageRequest);
      return SearchJobsResponse.builder()
          .withJobs(
              result.stream()
                  .map(
                      job -> {
                        return JobListingItem.builder()
                            .withDescription(job.getDescription())
                            .withExperience(job.getExperienceLevel())
                            .withId(job.getId().toString())
                            .withSkills(job.getSkills())
                            .withTitle(job.getTitle())
                            .build();
                      })
                  .toArray(JobListingItem[]::new))
          .withPagingState(getPagingState(result))
          .withStatusCode(HttpStatusCode.OK)
          .build();
    } catch (Exception e) {
      System.err.println("ERROR: " + e.getMessage());
      e.printStackTrace();
      return SearchJobsResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withErrorMessage(e.getMessage())
          .build();
    }
  }

  private static <T> String getPagingState(final Slice<T> slice) {
    if (slice.hasNext()) {
      CassandraPageRequest pageRequest = (CassandraPageRequest) slice.nextPageable();
      String pagingStateStr =
          com.datastax.oss.protocol.internal.util.Bytes.toHexString(pageRequest.getPagingState());
      return pagingStateStr;
    } else {
      return null;
    }
  }
}
