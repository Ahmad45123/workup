package com.workup.users.commands;

import com.workup.shared.commands.users.requests.RemoveFreelancerLanguageRequest;
import com.workup.shared.commands.users.responses.RemoveFreelancerLanguageResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class RemoveFreelancerLanguageCommand
    extends UserCommand<RemoveFreelancerLanguageRequest, RemoveFreelancerLanguageResponse> {
  @Override
  public RemoveFreelancerLanguageResponse Run(RemoveFreelancerLanguageRequest request) {
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());
    if (freelancerOptional.isEmpty())
      return RemoveFreelancerLanguageResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    Freelancer freelancer = freelancerOptional.get();
    freelancer.getLanguages().remove(request.getLanguageToRemove());
    freelancerRepository.save(freelancer);
    return RemoveFreelancerLanguageResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }
}
