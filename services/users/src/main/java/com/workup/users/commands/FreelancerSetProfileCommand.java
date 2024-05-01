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
    if (request.user_id == null) {
      freelancer = Freelancer.builder().withId(null).build();
    } else {

      Optional<Freelancer> freelancerOption = freelancerRepository.findById(request.user_id);
      if (!freelancerOption.isPresent()) {
        throw new RuntimeException("User not found");
      }
      freelancer = freelancerOption.get();
    }

    if (request.birth_date != null) {
      freelancer.setBirthdate(request.birth_date);
    }
    if (request.description != null) {
      freelancer.setDescription(request.description);
    }
    if (request.job_title != null) {
      freelancer.setJob_title(request.job_title);
    }
    if (request.city != null) {
      freelancer.setCity(request.city);
    }
    if (request.full_name != null) {
      freelancer.setFull_name(request.full_name);
    }
    if (request.email != null) {
      freelancer.setEmail(request.email);
    }

    freelancerRepository.save(freelancer);

    return FreelancerSetProfileResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }
}
