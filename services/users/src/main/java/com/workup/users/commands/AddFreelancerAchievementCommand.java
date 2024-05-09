package com.workup.users.commands;

import com.workup.shared.commands.users.requests.AddFreelancerAchievementRequest;
import com.workup.shared.commands.users.responses.AddFreelancerAchievementResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Achievement;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class AddFreelancerAchievementCommand
    extends UserCommand<AddFreelancerAchievementRequest, AddFreelancerAchievementResponse> {
  @Override
  public AddFreelancerAchievementResponse Run(AddFreelancerAchievementRequest request) {
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());
    if (freelancerOptional.isEmpty())
      return AddFreelancerAchievementResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    Freelancer freelancer = freelancerOptional.get();
    Achievement newAchievement =
        Achievement.builder()
            .withAchievement_description(request.getAchievement_description())
            .withAchievement_name(request.getAchievement_name())
            .withAward_date(request.getAward_date())
            .withAwarded_by(request.getAwarded_by())
            .build();
    newAchievement = achievementRepository.save(newAchievement);
    freelancer.getAchievements().add(newAchievement);
    freelancerRepository.save(freelancer);
    return AddFreelancerAchievementResponse.builder()
        .withStatusCode(HttpStatusCode.CREATED)
        .build();
  }
}
