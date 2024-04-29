package com.workup.users.commands;

import com.workup.shared.commands.users.requests.AddFreelancerLanguageRequest;
import com.workup.shared.commands.users.responses.AddFreelancerLanguageResponse;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class AddFreelancerLanguageCommand
    extends UserCommand<AddFreelancerLanguageRequest, AddFreelancerLanguageResponse> {

  @Override
  public AddFreelancerLanguageResponse Run(AddFreelancerLanguageRequest request) {
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUser_id());
    if (freelancerOptional.isEmpty())
      return AddFreelancerLanguageResponse.builder().withSuccess(false).build();
    Freelancer freelancer = freelancerOptional.get();
    freelancer.getLanguages().add(request.getNewLanguage());
    freelancerRepository.save(freelancer);
    return AddFreelancerLanguageResponse.builder().withSuccess(true).build();
  }
}
