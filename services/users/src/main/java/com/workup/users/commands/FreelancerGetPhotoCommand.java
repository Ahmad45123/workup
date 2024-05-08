package com.workup.users.commands;

import com.workup.shared.commands.users.requests.FreelancerGetPhotoRequest;
import com.workup.shared.commands.users.responses.FreelancerGetPhotoResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class FreelancerGetPhotoCommand
    extends UserCommand<FreelancerGetPhotoRequest, FreelancerGetPhotoResponse> {

  @Override
  public FreelancerGetPhotoResponse Run(FreelancerGetPhotoRequest request) {
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.user_id);

    if (!freelancerOptional.isPresent()) {
      return FreelancerGetPhotoResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .build();
    }

    Freelancer freelancer = freelancerOptional.get();

    return FreelancerGetPhotoResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withPhotoLink(freelancer.getPhoto_link())
        .build();
  }
}
