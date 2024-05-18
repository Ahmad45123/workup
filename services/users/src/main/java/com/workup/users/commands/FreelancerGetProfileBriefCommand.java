package com.workup.users.commands;

import com.workup.shared.commands.users.requests.FreelancerGetProfileBriefRequest;
import com.workup.shared.commands.users.responses.FreelancerGetProfileBriefResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FreelancerGetProfileBriefCommand
    extends UserCommand<FreelancerGetProfileBriefRequest, FreelancerGetProfileBriefResponse> {
  private static final Logger logger = LogManager.getLogger(FreelancerGetProfileBriefCommand.class);

  @Override
  public FreelancerGetProfileBriefResponse Run(FreelancerGetProfileBriefRequest request) {
    logger.info("[i] Getting Profile Brief for Freelancer with id: " + request.getUserId());
    Optional<Freelancer> freelancer = freelancerRepository.findById(request.getUserId());

    if (!freelancer.isPresent()) {
      logger.error("[x] Freelancer Doesn't Exist");
      return FreelancerGetProfileBriefResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .build();
    }

    return FreelancerGetProfileBriefResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withEmail(freelancer.get().getEmail())
        .withFullName(freelancer.get().getFullName())
        .build();
  }
}
