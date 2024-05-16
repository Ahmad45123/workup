package com.workup.users.commands;

import com.workup.shared.commands.users.requests.AddFreelancerLanguageRequest;
import com.workup.shared.commands.users.responses.AddFreelancerLanguageResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.ArrayList;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddFreelancerLanguageCommand
    extends UserCommand<AddFreelancerLanguageRequest, AddFreelancerLanguageResponse> {
  private static final Logger logger = LogManager.getLogger(AddFreelancerLanguageCommand.class);

  @Override
  public AddFreelancerLanguageResponse Run(AddFreelancerLanguageRequest request) {
    logger.info("Add Freelancer Language");
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());
    if (freelancerOptional.isEmpty()) {
      logger.error("Freelancer Not Found");
      return AddFreelancerLanguageResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    }
    Freelancer freelancer = freelancerOptional.get();
    if (freelancer.getLanguages() == null) freelancer.setLanguages(new ArrayList<>());
    freelancer.getLanguages().add(request.getNewLanguage());
    freelancerRepository.save(freelancer);
    return AddFreelancerLanguageResponse.builder().withStatusCode(HttpStatusCode.CREATED).build();
  }
}
