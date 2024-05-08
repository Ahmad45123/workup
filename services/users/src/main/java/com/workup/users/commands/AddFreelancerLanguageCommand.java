package com.workup.users.commands;

import com.workup.shared.commands.users.requests.AddFreelancerLanguageRequest;
import com.workup.shared.commands.users.responses.AddFreelancerLanguageResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class AddFreelancerLanguageCommand
    extends UserCommand<AddFreelancerLanguageRequest, AddFreelancerLanguageResponse> {

  @Override
  public AddFreelancerLanguageResponse Run(AddFreelancerLanguageRequest request) {
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUser_id());
    if (freelancerOptional.isEmpty())
      return AddFreelancerLanguageResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    Freelancer freelancer = freelancerOptional.get();
    freelancer.getLanguages().add(request.getNewLanguage());
    freelancerRepository.save(freelancer);
    return AddFreelancerLanguageResponse.builder().withStatusCode(HttpStatusCode.CREATED).build();
  }
}
