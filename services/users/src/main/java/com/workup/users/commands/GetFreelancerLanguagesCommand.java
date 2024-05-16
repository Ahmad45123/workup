package com.workup.users.commands;

import com.workup.shared.commands.users.requests.GetFreelancerLanguagesRequest;
import com.workup.shared.commands.users.responses.GetFreelancerLanguagesResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetFreelancerLanguagesCommand
    extends UserCommand<GetFreelancerLanguagesRequest, GetFreelancerLanguagesResponse> {
  private static final Logger logger = LogManager.getLogger(GetFreelancerLanguagesCommand.class);

  @Override
  public GetFreelancerLanguagesResponse Run(GetFreelancerLanguagesRequest request) {
    logger.info("Get Freelancer Languages");
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());
    if (freelancerOptional.isEmpty()) {
      logger.error("Freelancer Not Found");
      return GetFreelancerLanguagesResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    }
    Freelancer freelancer = freelancerOptional.get();
    List<String> languages = freelancer.getLanguages();
    return GetFreelancerLanguagesResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withLanguages(languages)
        .build();
  }
}
