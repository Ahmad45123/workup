package com.workup.users.commands;

import com.workup.shared.commands.users.requests.GetFreelancerAchievementsRequest;
import com.workup.shared.commands.users.responses.GetFreelancerAchievementsResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.views.users.AchievementView;
import com.workup.users.db.Achievement;
import com.workup.users.db.Freelancer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetFreelancerAchievementsCommand
    extends UserCommand<GetFreelancerAchievementsRequest, GetFreelancerAchievementsResponse> {
  private static final Logger logger = LogManager.getLogger(GetFreelancerAchievementsCommand.class);

  @Override
  public GetFreelancerAchievementsResponse Run(GetFreelancerAchievementsRequest request) {
    logger.info("Get Freelancer Achievements");
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());
    if (freelancerOptional.isEmpty()) {
      logger.info("Freelancer Not Found");
      return GetFreelancerAchievementsResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    }
    Freelancer freelancer = freelancerOptional.get();
    List<AchievementView> achievements = convertToAchievementViewList(freelancer.getAchievements());
    return GetFreelancerAchievementsResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withAchievements(achievements)
        .build();
  }

  public List<AchievementView> convertToAchievementViewList(List<Achievement> achievements) {
    List<AchievementView> achievementViews = new ArrayList<>();
    for (Achievement achievement : achievements) {
      AchievementView achievementView =
          AchievementView.builder()
              .withAchievement_name(achievement.getAchievement_name())
              .withAchievement_description(achievement.getAchievement_description())
              .withAward_date(achievement.getAward_date())
              .withAwarded_by(achievement.getAwarded_by())
              .build();
      achievementViews.add(achievementView);
    }
    return achievementViews;
  }
}
