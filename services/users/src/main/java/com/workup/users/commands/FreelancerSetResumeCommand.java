package com.workup.users.commands;

import com.workup.shared.commands.users.requests.FreelancerSetResumeRequest;
import com.workup.shared.commands.users.responses.FreelancerSetResumeResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class FreelancerSetResumeCommand
    extends UserCommand<FreelancerSetResumeRequest, FreelancerSetResumeResponse> {

  @Override
  public FreelancerSetResumeResponse Run(FreelancerSetResumeRequest request) {

    Optional<Freelancer> freelancerOption = freelancerRepository.findById(request.user_id);

    if (!freelancerOption.isPresent()) {
      throw new RuntimeException("User not found");
    }

    Freelancer freelancer = freelancerOption.get();

    freelancer.setResume_link(request.resumeLink);

    freelancerRepository.save(freelancer);

    return FreelancerSetResumeResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }
}
