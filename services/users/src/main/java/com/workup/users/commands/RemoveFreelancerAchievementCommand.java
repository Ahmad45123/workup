package com.workup.users.commands;

import com.workup.shared.commands.users.requests.RemoveFreelancerAchievementRequest;
import com.workup.shared.commands.users.responses.RemoveFreelancerAchievementResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Achievement;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class RemoveFreelancerAchievementCommand
    extends UserCommand<RemoveFreelancerAchievementRequest, RemoveFreelancerAchievementResponse> {
  @Override
  public RemoveFreelancerAchievementResponse Run(RemoveFreelancerAchievementRequest request) {
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());
    if (freelancerOptional.isEmpty())
      return RemoveFreelancerAchievementResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    Freelancer freelancer = freelancerOptional.get();
    freelancer
        .getAchievements()
        .removeIf(achievement -> achievement.getId().toString().equals(request.getAchievementId()));
    deleteAchievement(request.getAchievementId());
    freelancerRepository.save(freelancer);
    return RemoveFreelancerAchievementResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }

  public void deleteAchievement(String id) {
    Optional<Achievement> achievementOptional = achievementRepository.findById(id);
    if (achievementOptional.isEmpty()) return;
    achievementRepository.delete(achievementOptional.get());
  }
}
