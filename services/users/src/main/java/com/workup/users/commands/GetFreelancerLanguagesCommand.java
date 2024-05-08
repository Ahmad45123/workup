package com.workup.users.commands;

import com.workup.shared.commands.users.requests.GetFreelancerLanguagesRequest;
import com.workup.shared.commands.users.responses.GetFreelancerLanguagesResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.List;
import java.util.Optional;

public class GetFreelancerLanguagesCommand
    extends UserCommand<GetFreelancerLanguagesRequest, GetFreelancerLanguagesResponse> {
  @Override
  public GetFreelancerLanguagesResponse Run(GetFreelancerLanguagesRequest request) {
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUser_id());
    if (freelancerOptional.isEmpty())
      return GetFreelancerLanguagesResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    Freelancer freelancer = freelancerOptional.get();
    List<String> languages = freelancer.getLanguages();
    return GetFreelancerLanguagesResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withLanguages(languages)
        .build();
  }
}
