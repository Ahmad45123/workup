package com.workup.users.commands;

import com.workup.shared.commands.users.requests.GetFreelancerSkillsRequest;
import com.workup.shared.commands.users.responses.GetFreelancerSkillsResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetFreelancerSkillsCommand
    extends UserCommand<GetFreelancerSkillsRequest, GetFreelancerSkillsResponse> {
  private static final Logger logger = LogManager.getLogger(GetFreelancerSkillsCommand.class);

  @Override
  public GetFreelancerSkillsResponse Run(GetFreelancerSkillsRequest request) {
    logger.info("Get Freelancer Skills");
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());
    if (freelancerOptional.isEmpty()) {
      logger.error("Freelancer Not Found");
      return GetFreelancerSkillsResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    }
    Freelancer freelancer = freelancerOptional.get();
    List<String> skills = freelancer.getSkills();
    return GetFreelancerSkillsResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withSkills(skills)
        .build();
  }
}
