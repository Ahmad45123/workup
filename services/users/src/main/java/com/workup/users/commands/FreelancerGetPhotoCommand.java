package com.workup.users.commands;

import com.workup.shared.commands.users.requests.FreelancerGetPhotoRequest;
import com.workup.shared.commands.users.responses.FreelancerGetPhotoResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FreelancerGetPhotoCommand
    extends UserCommand<FreelancerGetPhotoRequest, FreelancerGetPhotoResponse> {
  private static final Logger logger = LogManager.getLogger(FreelancerGetPhotoCommand.class);

  @Override
  public FreelancerGetPhotoResponse Run(FreelancerGetPhotoRequest request) {
    logger.info("[i] Getting Photo for Freelancer with id: " + request.getUserId());
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());

    if (!freelancerOptional.isPresent()) {
      logger.error("[x] Freelancer Doesn't Exist");
      return FreelancerGetPhotoResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .build();
    }

    Freelancer freelancer = freelancerOptional.get();

    return FreelancerGetPhotoResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withPhotoLink(freelancer.getPhoto_link())
        .build();
  }
}
