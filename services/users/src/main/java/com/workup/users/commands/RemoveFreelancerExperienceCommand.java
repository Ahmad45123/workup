package com.workup.users.commands;

import com.workup.shared.commands.users.requests.RemoveFreelancerExperienceRequest;
import com.workup.shared.commands.users.responses.RemoveFreelancerExperienceResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Experience;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class RemoveFreelancerExperienceCommand
    extends UserCommand<RemoveFreelancerExperienceRequest, RemoveFreelancerExperienceResponse> {
  @Override
  public RemoveFreelancerExperienceResponse Run(RemoveFreelancerExperienceRequest request) {
    Optional<Freelancer> freelancerOptional =
        freelancerRepository.findById(request.getFreelancer_id());
    if (freelancerOptional.isEmpty())
      return RemoveFreelancerExperienceResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    Freelancer freelancer = freelancerOptional.get();
    freelancer
        .getExperiences()
        .removeIf(experience -> experience.getId().toString().equals(request.getExperience_id()));
    deleteExperience(request.getExperience_id());
    freelancerRepository.save(freelancer);
    return RemoveFreelancerExperienceResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }

  public void deleteExperience(String id) {
    Optional<Experience> experienceOptional = experienceRepository.findById(id);
    if (experienceOptional.isEmpty()) return;
    experienceRepository.delete(experienceOptional.get());
  }
}
