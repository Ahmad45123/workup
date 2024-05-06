package com.workup.users.commands;

import com.workup.shared.commands.users.requests.AddFreelancerEducationRequest;
import com.workup.shared.commands.users.responses.AddFreelancerEducationResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Education;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class AddFreelancerEducationCommand
    extends UserCommand<AddFreelancerEducationRequest, AddFreelancerEducationResponse> {

  @Override
  public AddFreelancerEducationResponse Run(AddFreelancerEducationRequest request) {
    Optional<Freelancer> freelancerOptional =
        freelancerRepository.findById(request.getUserId());
    if (freelancerOptional.isEmpty())
      return AddFreelancerEducationResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    Freelancer freelancer = freelancerOptional.get();
    Education newEducation =
        Education.builder()
            .withCity(request.getCity())
            .withDegree(request.getDegree())
            .withEducation_description(request.getEducation_description())
            .withEducation_start_date(request.getEducation_start_date())
            .withEnd_date(request.getEnd_date())
            .withGrade(request.getGrade())
            .withMajor(request.getMajor())
            .withSchool_name(request.getSchool_name())
            .build();
    newEducation = educationRepository.save(newEducation);
    freelancer.getEducations().add(newEducation);
    freelancerRepository.save(freelancer);
    return AddFreelancerEducationResponse.builder().withStatusCode(HttpStatusCode.CREATED).build();
  }
}
