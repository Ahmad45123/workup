package com.workup.users.commands;

import com.workup.shared.commands.users.requests.GetFreelancerAchievementsRequest;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.commands.responses.GetFreelancerAchievementsResponse;
import com.workup.users.db.Achievement;
import com.workup.users.db.Freelancer;
import java.util.List;
import java.util.Optional;

public class GetFreelancerAchievementsCommand
    extends UserCommand<GetFreelancerAchievementsRequest, GetFreelancerAchievementsResponse> {
  @Override
  public GetFreelancerAchievementsResponse Run(GetFreelancerAchievementsRequest request) {
    Optional<Freelancer> freelancerOptional =
        freelancerRepository.findById(request.getFreelancer_id());
    if (freelancerOptional.isEmpty())
      return GetFreelancerAchievementsResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    Freelancer freelancer = freelancerOptional.get();
    List<Achievement> achievements = freelancer.getAchievements();
    return GetFreelancerAchievementsResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withAchievements(achievements)
        .build();
  }
}
