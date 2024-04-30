package com.workup.users.commands;

import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.commands.requests.UpdateFreelancerExperienceRequest;
import com.workup.users.commands.responses.UpdateFreelancerExperienceResponse;
import com.workup.users.db.Experience;
import com.workup.users.db.Freelancer;
import java.util.Optional;
import org.springframework.beans.BeanUtils;

public class UpdateFreelancerExperienceCommand
    extends UserCommand<UpdateFreelancerExperienceRequest, UpdateFreelancerExperienceResponse> {
  @Override
  public UpdateFreelancerExperienceResponse Run(UpdateFreelancerExperienceRequest request) {
    Optional<Freelancer> freelancerOptional =
        freelancerRepository.findById(request.getFreelancer_id());
    if (freelancerOptional.isEmpty())
      return UpdateFreelancerExperienceResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    Freelancer freelancer = freelancerOptional.get();
    updateExperience(
        request.getUpdatedExperience().getId().toString(), request.getUpdatedExperience());
    return UpdateFreelancerExperienceResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withFreelancer(freelancer)
        .build();
  }

  public void updateExperience(String id, Experience updatedExperience) {
    Optional<Experience> experienceOptional = experienceRepository.findById(id);
    if (experienceOptional.isEmpty()) return;
    Experience existingExperience = experienceOptional.get();
    BeanUtils.copyProperties(updatedExperience, existingExperience, "id");
    experienceRepository.save(existingExperience);
  }
}
