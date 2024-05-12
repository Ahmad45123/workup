package com.workup.users.commands;

import com.workup.shared.commands.users.requests.FreelancerSetProfileRequest;
import com.workup.shared.commands.users.responses.FreelancerSetProfileResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class FreelancerSetProfileCommand
    extends UserCommand<FreelancerSetProfileRequest, FreelancerSetProfileResponse> {

  @Override
  public FreelancerSetProfileResponse Run(FreelancerSetProfileRequest request) {

    Freelancer freelancer;
    if (request.getUserId() == null) {
      freelancer = Freelancer.builder().withId(null).build();
    } else {

      Optional<Freelancer> freelancerOption = freelancerRepository.findById(request.getUserId());
      if (!freelancerOption.isPresent()) {
        throw new RuntimeException("User not found");
      }
      freelancer = freelancerOption.get();
    }

    if (request.getBirthDate() != null) {
      freelancer.setBirthdate(request.getBirthDate());
    }
    if (request.description != null) {
      freelancer.setDescription(request.description);
    }
    if (request.getJobTitle() != null) {
      freelancer.setJob_title(request.getJobTitle());
    }
    if (request.city != null) {
      freelancer.setCity(request.city);
    }
    if (request.fullName != null) {
      freelancer.setFullName(request.fullName);
    }
    if (request.email != null) {
      freelancer.setEmail(request.email);
    }

    freelancerRepository.save(freelancer);
    return FreelancerSetProfileResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }
}
