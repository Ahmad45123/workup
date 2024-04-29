package com.workup.users.commands;

import com.workup.users.commands.requests.RemoveFreelancerExperienceRequest;
import com.workup.users.commands.responses.RemoveFreelancerExperienceResponse;
import com.workup.users.db.Experience;
import com.workup.users.db.Freelancer;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

public class RemoveFreelancerExperienceCommand
    extends UserCommand<RemoveFreelancerExperienceRequest, RemoveFreelancerExperienceResponse> {
  @Override
  public RemoveFreelancerExperienceResponse Run(RemoveFreelancerExperienceRequest request) {
    Optional<Freelancer> freelancerOptional =
        freelancerRepository.findById(request.getFreelancer_id());
    if (freelancerOptional.isEmpty())
      return RemoveFreelancerExperienceResponse.builder().withSuccess(false).build();
    Freelancer freelancer = freelancerOptional.get();
    freelancer
        .getExperiences()
        .removeIf(experience -> experience.getId().toString().equals(request.getExperience_id()));
    deleteExperience(request.getExperience_id());
    freelancerRepository.save(freelancer);
    return RemoveFreelancerExperienceResponse.builder()
        .withSuccess(true)
        .withFreelancer(freelancer)
        .build();
  }

  public void deleteExperience(String id) {
    Optional<Experience> experienceOptional = experienceRepository.findById(id);
    if (experienceOptional.isEmpty()) {
      ResponseEntity.notFound().build();
      return;
    }
    experienceRepository.delete(experienceOptional.get());
    ResponseEntity.noContent().build();
  }
}
