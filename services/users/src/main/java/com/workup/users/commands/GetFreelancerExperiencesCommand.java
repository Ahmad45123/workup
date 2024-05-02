package com.workup.users.commands;

import com.workup.shared.commands.users.requests.GetFreelancerExperiencesRequest;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.commands.responses.GetFreelancerExperiencesResponse;
import com.workup.users.db.Experience;
import com.workup.users.db.Freelancer;
import java.util.List;
import java.util.Optional;

public class GetFreelancerExperiencesCommand
    extends UserCommand<GetFreelancerExperiencesRequest, GetFreelancerExperiencesResponse> {

  @Override
  public GetFreelancerExperiencesResponse Run(GetFreelancerExperiencesRequest request) {
    Optional<Freelancer> freelancerOptional =
        freelancerRepository.findById(request.getFreelancerId());
    if (freelancerOptional.isEmpty())
      return GetFreelancerExperiencesResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    Freelancer freelancer = freelancerOptional.get();
    List<Experience> experiences = freelancer.getExperiences();
    return GetFreelancerExperiencesResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withExperiences(experiences)
        .build();
  }
}
