package com.workup.users.commands;

import com.workup.shared.commands.users.requests.UpdateFreelancerExperienceRequest;
import com.workup.shared.commands.users.responses.UpdateFreelancerExperienceResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Experience;
import com.workup.users.db.Freelancer;
import java.util.Optional;

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
    Experience updatedExperience =
        Experience.builder()
            .withExperience_description(request.getNew_experience_description())
            .withCity(request.getNew_city())
            .withCompany_name(request.getNew_company_name())
            .withEmployment_end(request.getNew_employment_end())
            .withEmployment_start(request.getNew_employment_start())
            .withJob_title(request.getNew_job_title())
            .build();
    updateExperience(request.getExperience_id(), updatedExperience);
    freelancerRepository.save(freelancer);
    return UpdateFreelancerExperienceResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }

  public void updateExperience(String id, Experience updatedExperience) {
    Optional<Experience> experienceOptional = experienceRepository.findById(id);
    if (experienceOptional.isEmpty()) return;
    Experience existingExperience = experienceOptional.get();
    if (updatedExperience.getExperience_description() != null) {
      existingExperience.setExperience_description(updatedExperience.getExperience_description());
    }
    if (updatedExperience.getCity() != null) {
      existingExperience.setCity(updatedExperience.getCity());
    }
    if (updatedExperience.getCompany_name() != null) {
      existingExperience.setCompany_name(updatedExperience.getCompany_name());
    }
    if (updatedExperience.getEmployment_end() != null) {
      existingExperience.setEmployment_end(updatedExperience.getEmployment_end());
    }
    if (updatedExperience.getEmployment_start() != null) {
      existingExperience.setEmployment_start(updatedExperience.getEmployment_start());
    }
    if (updatedExperience.getJob_title() != null) {
      existingExperience.setJob_title(updatedExperience.getJob_title());
    }

    experienceRepository.save(existingExperience);
  }
}
