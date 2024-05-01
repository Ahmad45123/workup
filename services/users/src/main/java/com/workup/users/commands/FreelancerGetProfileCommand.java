package com.workup.users.commands;

import com.workup.shared.commands.users.requests.FreelancerGetProfileRequest;
import com.workup.shared.commands.users.responses.FreelancerGetProfileResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class FreelancerGetProfileCommand
    extends UserCommand<FreelancerGetProfileRequest, FreelancerGetProfileResponse> {

  @Override
  public FreelancerGetProfileResponse Run(FreelancerGetProfileRequest request) {
    Optional<Freelancer> freelancer = freelancerRepository.findById(request.user_id);

    if (!freelancer.isPresent()) {
      return FreelancerGetProfileResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .build();
    }

    return FreelancerGetProfileResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withBirth_date(freelancer.get().getBirthdate())
        .withCity(freelancer.get().getCity())
        .withDescription(freelancer.get().getDescription())
        .withEmail(freelancer.get().getEmail())
        .withFull_name(freelancer.get().getFull_name())
        .withJob_title(freelancer.get().getJob_title())
        .withLanguages(freelancer.get().getLanguages())
        .withSkills(freelancer.get().getSkills())
        .build();
  }
}
