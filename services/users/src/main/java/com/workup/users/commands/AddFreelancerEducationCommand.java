package com.workup.users.commands;

import com.workup.shared.commands.users.responses.AddFreelancerEducationResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.commands.requests.AddFreelancerEducationRequest;
import com.workup.users.db.Education;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class AddFreelancerEducationCommand
    extends UserCommand<AddFreelancerEducationRequest, AddFreelancerEducationResponse> {

  @Override
  public AddFreelancerEducationResponse Run(AddFreelancerEducationRequest request) {
    Optional<Freelancer> freelancerOptional =
        freelancerRepository.findById(request.getFreelancer_id());
    if (freelancerOptional.isEmpty())
      return AddFreelancerEducationResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    Freelancer freelancer = freelancerOptional.get();
    Education newEducation = educationRepository.save(request.getNewEducation());
    freelancer.getEducations().add(newEducation);
    freelancerRepository.save(freelancer);
    return AddFreelancerEducationResponse.builder()
        .withStatusCode(HttpStatusCode.CREATED)
        .withFreelancer(freelancer)
        .build();
  }
}
