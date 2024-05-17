package com.workup.users.commands;

import com.workup.shared.commands.users.requests.UpdateFreelancerAchievementRequest;
import com.workup.shared.commands.users.responses.UpdateFreelancerAchievementResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Achievement;
import com.workup.users.db.Freelancer;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateFreelancerAchievementCommand
    extends UserCommand<UpdateFreelancerAchievementRequest, UpdateFreelancerAchievementResponse> {
  private static final Logger logger =
      LogManager.getLogger(UpdateFreelancerAchievementCommand.class);

  @Override
  public UpdateFreelancerAchievementResponse Run(UpdateFreelancerAchievementRequest request) {
    logger.info("Update Freelancer Achievement - Freelancer ID: " + request.getUserId());
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());
    if (freelancerOptional.isEmpty()) {
      logger.error("Freelancer Not Found - Freelancer ID: " + request.getUserId());
      return UpdateFreelancerAchievementResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    }
    Freelancer freelancer = freelancerOptional.get();
    Achievement updatedAchievement =
        Achievement.builder()
            .withAchievement_description(request.getNew_achievement_description())
            .withAchievement_name(request.getNew_achievement_name())
            .withAward_date(request.getNew_award_date())
            .withAwarded_by(request.getNew_awarded_by())
            .build();
    updateAchievement(request.getAchievement_id(), updatedAchievement);
    freelancerRepository.save(freelancer);
    return UpdateFreelancerAchievementResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }

  public void updateAchievement(String id, Achievement updatedAchievement) {
    Optional<Achievement> achievementOptional = achievementRepository.findById(id);
    if (achievementOptional.isEmpty()) return;
    Achievement existingAchievement = achievementOptional.get();
    if (updatedAchievement.getAchievement_name() != null) {
      existingAchievement.setAchievement_name(updatedAchievement.getAchievement_name());
    }
    if (updatedAchievement.getAchievement_description() != null) {
      existingAchievement.setAchievement_description(
          updatedAchievement.getAchievement_description());
    }
    if (updatedAchievement.getAward_date() != null) {
      existingAchievement.setAward_date(updatedAchievement.getAward_date());
    }
    if (updatedAchievement.getAwarded_by() != null) {
      existingAchievement.setAwarded_by(updatedAchievement.getAwarded_by());
    }
    achievementRepository.save(existingAchievement);
  }
}
