package com.workup.users.commands;

import com.workup.users.commands.requests.GetFreelancerEducationsRequest;
import com.workup.users.commands.responses.GetFreelancerEducationsResponse;
import com.workup.users.db.Education;
import com.workup.users.db.Freelancer;
import java.util.List;
import java.util.Optional;

public class GetFreelancerEducationsCommand
    extends UserCommand<GetFreelancerEducationsRequest, GetFreelancerEducationsResponse> {
  @Override
  public GetFreelancerEducationsResponse Run(GetFreelancerEducationsRequest request) {
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUser_id());
    if (freelancerOptional.isEmpty())
      return GetFreelancerEducationsResponse.builder().withSuccess(false).build();
    Freelancer freelancer = freelancerOptional.get();
    List<Education> educations = freelancer.getEducations();
    return GetFreelancerEducationsResponse.builder()
        .withSuccess(true)
        .withEducations(educations)
        .build();
  }
}
