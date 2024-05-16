package com.workup.users.commands;

import com.workup.shared.commands.users.requests.FreelancerGetResumeRequest;
import com.workup.shared.commands.users.responses.FreelancerGetResumeResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FreelancerGetResumeCommand
    extends UserCommand<FreelancerGetResumeRequest, FreelancerGetResumeResponse> {
  private static final Logger logger = LogManager.getLogger(FreelancerGetResumeCommand.class);

  @Override
  public FreelancerGetResumeResponse Run(FreelancerGetResumeRequest request) {
    logger.info("[i] Getting Resume for Freelancer with id: " + request.getUserId());
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());

    if (!freelancerOptional.isPresent()) {
      logger.error("[x] Freelancer Doesn't Exist");
      return FreelancerGetResumeResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .build();
    }

    Freelancer freelancer = freelancerOptional.get();

    return FreelancerGetResumeResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withResumeLink(freelancer.getResume_link())
        .build();
  }
}
