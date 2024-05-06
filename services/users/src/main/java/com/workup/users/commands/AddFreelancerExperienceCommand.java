package com.workup.users.commands;

import com.workup.shared.commands.users.requests.AddFreelancerExperienceRequest;
import com.workup.shared.commands.users.responses.AddFreelancerExperienceResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Experience;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class AddFreelancerExperienceCommand
    extends UserCommand<AddFreelancerExperienceRequest, AddFreelancerExperienceResponse> {
  @Override
  public AddFreelancerExperienceResponse Run(AddFreelancerExperienceRequest request) {
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());
    if (freelancerOptional.isEmpty())
      return AddFreelancerExperienceResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    Freelancer freelancer = freelancerOptional.get();
    Experience newExperience =
        Experience.builder()
            .withExperience_description(request.getExperience_description())
            .withCity(request.getCity())
            .withCompany_name(request.getCompany_name())
            .withEmployment_end(request.getEmployment_end())
            .withEmployment_start(request.getEmployment_start())
            .withJob_title(request.getJob_title())
            .build();
    newExperience = experienceRepository.save(newExperience);
    freelancer.getExperiences().add(newExperience);
    freelancerRepository.save(freelancer);
    return AddFreelancerExperienceResponse.builder().withStatusCode(HttpStatusCode.CREATED).build();
  }
}
