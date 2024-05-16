package com.workup.users.commands;

import com.workup.shared.commands.users.requests.FreelancerSetPhotoRequest;
import com.workup.shared.commands.users.responses.FreelancerSetPhotoResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FreelancerSetPhotoCommand
    extends UserCommand<FreelancerSetPhotoRequest, FreelancerSetPhotoResponse> {
  private static final Logger logger = LogManager.getLogger(FreelancerSetPhotoCommand.class);

  @Override
  public FreelancerSetPhotoResponse Run(FreelancerSetPhotoRequest request) {
    logger.info("[i] Setting Photo for Freelancer with id: " + request.getUserId());
    Optional<Freelancer> freelancerOption = freelancerRepository.findById(request.getUserId());

    if (!freelancerOption.isPresent()) {
      logger.error("[x] Freelancer Doesn't Exist");
      throw new RuntimeException("User not found");
    }

    Freelancer freelancer = freelancerOption.get();

    freelancer.setPhoto_link(request.photoLink);

    freelancerRepository.save(freelancer);

    return FreelancerSetPhotoResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }
}
