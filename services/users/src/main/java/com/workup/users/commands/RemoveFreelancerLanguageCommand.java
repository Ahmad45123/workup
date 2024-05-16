package com.workup.users.commands;

import com.workup.shared.commands.users.requests.RemoveFreelancerLanguageRequest;
import com.workup.shared.commands.users.responses.RemoveFreelancerLanguageResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemoveFreelancerLanguageCommand
    extends UserCommand<RemoveFreelancerLanguageRequest, RemoveFreelancerLanguageResponse> {
  private static final Logger logger = LogManager.getLogger(RemoveFreelancerLanguageCommand.class);

  @Override
  public RemoveFreelancerLanguageResponse Run(RemoveFreelancerLanguageRequest request) {
    logger.info("Remove Freelancer Language");
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());
    if (freelancerOptional.isEmpty()) {
      logger.error("Freelancer Not Found");
      return RemoveFreelancerLanguageResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    }
    Freelancer freelancer = freelancerOptional.get();
    freelancer.getLanguages().remove(request.getLanguageToRemove());
    freelancerRepository.save(freelancer);
    return RemoveFreelancerLanguageResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }
}
