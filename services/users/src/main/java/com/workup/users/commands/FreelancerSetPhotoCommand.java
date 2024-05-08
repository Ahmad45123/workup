package com.workup.users.commands;

import com.workup.shared.commands.users.requests.FreelancerSetPhotoRequest;
import com.workup.shared.commands.users.responses.FreelancerSetPhotoResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class FreelancerSetPhotoCommand
    extends UserCommand<FreelancerSetPhotoRequest, FreelancerSetPhotoResponse> {

  @Override
  public FreelancerSetPhotoResponse Run(FreelancerSetPhotoRequest request) {

    Optional<Freelancer> freelancerOption = freelancerRepository.findById(request.user_id);

    if (!freelancerOption.isPresent()) {
      throw new RuntimeException("User not found");
    }

    Freelancer freelancer = freelancerOption.get();

    freelancer.setPhoto_link(request.photoLink);

    freelancerRepository.save(freelancer);

    return FreelancerSetPhotoResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }
}
