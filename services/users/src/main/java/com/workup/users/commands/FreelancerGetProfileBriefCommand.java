package com.workup.users.commands;

import com.workup.shared.commands.users.requests.FreelancerGetProfileBriefRequest;
import com.workup.shared.commands.users.responses.FreelancerGetProfileBriefResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class FreelancerGetProfileBriefCommand
    extends UserCommand<FreelancerGetProfileBriefRequest, FreelancerGetProfileBriefResponse> {

  @Override
  public FreelancerGetProfileBriefResponse Run(FreelancerGetProfileBriefRequest request) {
    Optional<Freelancer> freelancer = freelancerRepository.findById(request.getUserId());

    if (!freelancer.isPresent()) {
      return FreelancerGetProfileBriefResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .build();
    }

    return FreelancerGetProfileBriefResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withEmail(freelancer.get().getEmail())
        .withFullName(freelancer.get().getFullName())
        .build();
  }
}
