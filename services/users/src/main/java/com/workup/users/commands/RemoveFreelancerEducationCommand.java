package com.workup.users.commands;

import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.commands.requests.RemoveFreelancerEducationRequest;
import com.workup.users.commands.responses.RemoveFreelancerEducationResponse;
import com.workup.users.db.Education;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class RemoveFreelancerEducationCommand
    extends UserCommand<RemoveFreelancerEducationRequest, RemoveFreelancerEducationResponse> {

  @Override
  public RemoveFreelancerEducationResponse Run(RemoveFreelancerEducationRequest request) {
    Optional<Freelancer> freelancerOptional =
        freelancerRepository.findById(request.getFreelancer_id());
    if (freelancerOptional.isEmpty())
      return RemoveFreelancerEducationResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    Freelancer freelancer = freelancerOptional.get();
    freelancer
        .getEducations()
        .removeIf(education -> education.getId().toString().equals(request.getEducation_id()));
    deleteEducation(request.getEducation_id());
    freelancerRepository.save(freelancer);
    return RemoveFreelancerEducationResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withFreelancer(freelancer)
        .build();
  }

  void deleteEducation(String id) {
    Optional<Education> educationOptional = educationRepository.findById(id);
    if (educationOptional.isEmpty()) return;
    educationRepository.delete(educationOptional.get());
  }
}
