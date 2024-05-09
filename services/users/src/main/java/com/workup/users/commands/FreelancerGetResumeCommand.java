package com.workup.users.commands;

import com.workup.shared.commands.users.requests.FreelancerGetResumeRequest;
import com.workup.shared.commands.users.responses.FreelancerGetResumeResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class FreelancerGetResumeCommand
    extends UserCommand<FreelancerGetResumeRequest, FreelancerGetResumeResponse> {

  @Override
  public FreelancerGetResumeResponse Run(FreelancerGetResumeRequest request) {
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());

    if (!freelancerOptional.isPresent()) {
      return FreelancerGetResumeResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .build();
    }

    Freelancer freelancer = freelancerOptional.get();

    return FreelancerGetResumeResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withResumeLink(freelancer.getResume_link())
        .build();
  }
}
