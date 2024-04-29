package com.workup.users.commands;

import com.workup.users.commands.requests.RemoveFreelancerAchievementRequest;
import com.workup.users.commands.responses.RemoveFreelancerAchievementResponse;
import com.workup.users.db.Achievement;
import com.workup.users.db.Freelancer;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

public class RemoveFreelancerAchievementCommand
    extends UserCommand<RemoveFreelancerAchievementRequest, RemoveFreelancerAchievementResponse> {
  @Override
  public RemoveFreelancerAchievementResponse Run(RemoveFreelancerAchievementRequest request) {
    Optional<Freelancer> freelancerOptional =
        freelancerRepository.findById(request.getFreelancerId());
    if (freelancerOptional.isEmpty())
      return RemoveFreelancerAchievementResponse.builder().withSuccess(false).build();
    Freelancer freelancer = freelancerOptional.get();
    freelancer
        .getAchievements()
        .removeIf(achievement -> achievement.getId().toString().equals(request.getAchievementId()));
    deleteAchievement(request.getAchievementId());
    freelancerRepository.save(freelancer);
    return RemoveFreelancerAchievementResponse.builder()
        .withSuccess(true)
        .withFreelancer(freelancer)
        .build();
  }

  public void deleteAchievement(String id) {
    Optional<Achievement> achievementOptional = achievementRepository.findById(id);
    if (achievementOptional.isEmpty()) {
      ResponseEntity.notFound().build();
      return;
    }
    achievementRepository.delete(achievementOptional.get());
    ResponseEntity.noContent().build();
  }
}
