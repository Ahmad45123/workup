package com.workup.users.commands;

import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.commands.requests.UpdateFreelancerAchievementRequest;
import com.workup.users.commands.responses.UpdateFreelancerAchievementResponse;
import com.workup.users.db.Achievement;
import com.workup.users.db.Freelancer;
import java.util.Optional;
import org.springframework.beans.BeanUtils;

public class UpdateFreelancerAchievementCommand
    extends UserCommand<UpdateFreelancerAchievementRequest, UpdateFreelancerAchievementResponse> {
  @Override
  public UpdateFreelancerAchievementResponse Run(UpdateFreelancerAchievementRequest request) {
    Optional<Freelancer> freelancerOptional =
        freelancerRepository.findById(request.getFreelancer_id());
    if (freelancerOptional.isEmpty())
      return UpdateFreelancerAchievementResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    Freelancer freelancer = freelancerOptional.get();
    updateAchievement(request.getUpdatedAchievement());
    freelancerRepository.save(freelancer);
    return UpdateFreelancerAchievementResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withFreelancer(freelancer)
        .build();
  }

  public void updateAchievement(Achievement updatedAchievement) {
    String id = updatedAchievement.getId().toString();
    Optional<Achievement> achievementOptional = achievementRepository.findById(id);
    if (achievementOptional.isEmpty()) return;
    Achievement existingAchievement = achievementOptional.get();
    BeanUtils.copyProperties(updatedAchievement, existingAchievement, "id");
    achievementRepository.save(existingAchievement);
  }
}
