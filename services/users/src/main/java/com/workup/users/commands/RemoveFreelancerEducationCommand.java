package com.workup.users.commands;

import com.workup.shared.commands.users.requests.RemoveFreelancerEducationRequest;
import com.workup.shared.commands.users.responses.RemoveFreelancerEducationResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Education;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class RemoveFreelancerEducationCommand
    extends UserCommand<RemoveFreelancerEducationRequest, RemoveFreelancerEducationResponse> {

  @Override
  public RemoveFreelancerEducationResponse Run(RemoveFreelancerEducationRequest request) {
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());
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
    return RemoveFreelancerEducationResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }

  void deleteEducation(String id) {
    Optional<Education> educationOptional = educationRepository.findById(id);
    if (educationOptional.isEmpty()) return;
    educationRepository.delete(educationOptional.get());
  }
}
