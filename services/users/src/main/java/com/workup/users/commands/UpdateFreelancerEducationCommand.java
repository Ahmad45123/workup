package com.workup.users.commands;

import com.workup.shared.commands.users.requests.UpdateFreelancerEducationRequest;
import com.workup.shared.commands.users.responses.UpdateFreelancerEducationResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Education;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class UpdateFreelancerEducationCommand
    extends UserCommand<UpdateFreelancerEducationRequest, UpdateFreelancerEducationResponse> {

  @Override
  public UpdateFreelancerEducationResponse Run(UpdateFreelancerEducationRequest request) {
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());
    if (freelancerOptional.isEmpty())
      return UpdateFreelancerEducationResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    Freelancer freelancer = freelancerOptional.get();
    Education updatedEducation =
        Education.builder()
            .withCity(request.getNew_city())
            .withDegree(request.getNew_degree())
            .withEducation_description(request.getNew_education_description())
            .withEducation_start_date(request.getNew_education_start_date())
            .withEnd_date(request.getNew_end_date())
            .withGrade(request.getNew_grade())
            .withMajor(request.getNew_major())
            .withSchool_name(request.getNew_school_name())
            .build();
    updateEducation(request.getEducation_id(), updatedEducation);
    freelancerRepository.save(freelancer);
    return UpdateFreelancerEducationResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }

  void updateEducation(String id, Education updatedEducation) {
    Optional<Education> educationOptional = educationRepository.findById(id);
    if (educationOptional.isEmpty()) return;
    Education existingEducation = educationOptional.get();
    if (updatedEducation.getCity() != null) {
      existingEducation.setCity(updatedEducation.getCity());
    }
    if (updatedEducation.getDegree() != null) {
      existingEducation.setDegree(updatedEducation.getDegree());
    }
    if (updatedEducation.getEducation_start_date() != null) {
      existingEducation.setEducation_start_date(updatedEducation.getEducation_start_date());
    }
    if (updatedEducation.getEnd_date() != null) {
      existingEducation.setEnd_date(updatedEducation.getEnd_date());
    }
    if (updatedEducation.getEducation_description() != null) {
      existingEducation.setEducation_description(updatedEducation.getEducation_description());
    }
    if (updatedEducation.getGrade() != null) {
      existingEducation.setGrade(updatedEducation.getGrade());
    }
    if (updatedEducation.getMajor() != null) {
      existingEducation.setMajor(updatedEducation.getMajor());
    }
    if (updatedEducation.getSchool_name() != null) {
      existingEducation.setSchool_name(updatedEducation.getSchool_name());
    }

    educationRepository.save(existingEducation);
  }
}
