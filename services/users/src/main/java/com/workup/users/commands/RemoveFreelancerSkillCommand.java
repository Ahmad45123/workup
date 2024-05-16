package com.workup.users.commands;

import com.workup.shared.commands.users.requests.RemoveFreelancerSkillRequest;
import com.workup.shared.commands.users.responses.RemoveFreelancerSkillResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemoveFreelancerSkillCommand
    extends UserCommand<RemoveFreelancerSkillRequest, RemoveFreelancerSkillResponse> {
  private static final Logger logger = LogManager.getLogger(RemoveFreelancerSkillCommand.class);

  @Override
  public RemoveFreelancerSkillResponse Run(RemoveFreelancerSkillRequest request) {
    logger.info("Remove Freelancer Skill - Freelancer ID: " + request.getUserId());
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());
    if (freelancerOptional.isEmpty()) {
      logger.error("Freelancer Not Found - Freelancer ID: " + request.getUserId());
      return RemoveFreelancerSkillResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    }
    Freelancer freelancer = freelancerOptional.get();
    freelancer.getSkills().remove(request.getSkillToRemove());
    freelancerRepository.save(freelancer);
    return RemoveFreelancerSkillResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }
}
