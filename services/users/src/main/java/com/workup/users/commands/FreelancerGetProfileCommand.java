package com.workup.users.commands;

import com.workup.shared.commands.users.requests.FreelancerGetProfileRequest;
import com.workup.shared.commands.users.responses.FreelancerGetProfileResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FreelancerGetProfileCommand
    extends UserCommand<FreelancerGetProfileRequest, FreelancerGetProfileResponse> {
  private static final Logger logger = LogManager.getLogger(FreelancerGetProfileCommand.class);

  @Override
  public FreelancerGetProfileResponse Run(FreelancerGetProfileRequest request) {
    logger.info("[i] Getting Profile for Freelancer with id: " + request.getUserId());
    Optional<Freelancer> freelancer = freelancerRepository.findById(request.getUserId());

    if (!freelancer.isPresent()) {
      logger.error("[x] Freelancer Doesn't Exist");
      return FreelancerGetProfileResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .build();
    }

    return FreelancerGetProfileResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withBirthDate(freelancer.get().getBirthdate())
        .withCity(freelancer.get().getCity())
        .withDescription(freelancer.get().getDescription())
        .withEmail(freelancer.get().getEmail())
        .withFullName(freelancer.get().getFullName())
        .withJobTitle(freelancer.get().getJob_title())
        .withLanguages(freelancer.get().getLanguages())
        .withSkills(freelancer.get().getSkills())
        .build();
  }
}
