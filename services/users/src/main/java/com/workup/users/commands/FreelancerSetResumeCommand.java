package com.workup.users.commands;

import com.workup.shared.commands.users.requests.FreelancerSetResumeRequest;
import com.workup.shared.commands.users.responses.FreelancerSetResumeResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FreelancerSetResumeCommand
    extends UserCommand<FreelancerSetResumeRequest, FreelancerSetResumeResponse> {
  private static final Logger logger = LogManager.getLogger(FreelancerSetResumeCommand.class);

  @Override
  public FreelancerSetResumeResponse Run(FreelancerSetResumeRequest request) {
    logger.info("[i] Setting Resume for Freelancer with id: " + request.getUserId());
    Optional<Freelancer> freelancerOption = freelancerRepository.findById(request.getUserId());

    if (!freelancerOption.isPresent()) {
      logger.error("[x] Freelancer Doesn't Exist");
      throw new RuntimeException("User not found");
    }

    Freelancer freelancer = freelancerOption.get();

    freelancer.setResume_link(request.resumeLink);

    freelancerRepository.save(freelancer);

    return FreelancerSetResumeResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }
}
