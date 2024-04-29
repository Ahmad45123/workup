package com.workup.users.commands;

import com.workup.users.commands.requests.UpdateFreelancerEducationRequest;
import com.workup.users.commands.responses.UpdateFreelancerEducationResponse;
import com.workup.users.db.Education;
import com.workup.users.db.Freelancer;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;

public class UpdateFreelancerEducationCommand
    extends UserCommand<UpdateFreelancerEducationRequest, UpdateFreelancerEducationResponse> {

  @Override
  public UpdateFreelancerEducationResponse Run(UpdateFreelancerEducationRequest request) {
    Optional<Freelancer> freelancerOptional =
        freelancerRepository.findById(request.getFreelancer_id());
    if (freelancerOptional.isEmpty())
      return UpdateFreelancerEducationResponse.builder().withSuccess(false).build();
    Freelancer freelancer = freelancerOptional.get();
    updateEducation(request.getEducation_id(), request.getUpdatedEducation());
    return UpdateFreelancerEducationResponse.builder()
        .withSuccess(true)
        .withFreelancer(freelancer)
        .build();
  }

  void updateEducation(String id, Education updatedEducation) {
    Optional<Education> educationOptional = educationRepository.findById(id);
    if (educationOptional.isEmpty()) {
      ResponseEntity.notFound().build();
      return;
    }
    Education existingEducation = educationOptional.get();
    BeanUtils.copyProperties(updatedEducation, existingEducation, "id");
    educationRepository.save(existingEducation);
    ResponseEntity.ok(existingEducation);
  }
}
