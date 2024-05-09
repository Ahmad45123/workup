package com.workup.users.commands;

import com.workup.shared.commands.users.requests.RemoveFreelancerSkillRequest;
import com.workup.shared.commands.users.responses.RemoveFreelancerSkillResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class RemoveFreelancerSkillCommand
    extends UserCommand<RemoveFreelancerSkillRequest, RemoveFreelancerSkillResponse> {
  @Override
  public RemoveFreelancerSkillResponse Run(RemoveFreelancerSkillRequest request) {
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());
    if (freelancerOptional.isEmpty())
      return RemoveFreelancerSkillResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    Freelancer freelancer = freelancerOptional.get();
    freelancer.getSkills().remove(request.getSkillToRemove());
    freelancerRepository.save(freelancer);
    return RemoveFreelancerSkillResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }
}
