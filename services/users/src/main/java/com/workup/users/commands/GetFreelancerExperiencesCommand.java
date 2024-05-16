package com.workup.users.commands;

import com.workup.shared.commands.users.requests.GetFreelancerExperiencesRequest;
import com.workup.shared.commands.users.responses.GetFreelancerExperiencesResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.views.users.ExperienceView;
import com.workup.users.db.Experience;
import com.workup.users.db.Freelancer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetFreelancerExperiencesCommand
    extends UserCommand<GetFreelancerExperiencesRequest, GetFreelancerExperiencesResponse> {
  private static final Logger logger = LogManager.getLogger(GetFreelancerExperiencesCommand.class);

  @Override
  public GetFreelancerExperiencesResponse Run(GetFreelancerExperiencesRequest request) {
    logger.info("Get Freelancer Experiences");
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());
    if (freelancerOptional.isEmpty()) {
      logger.info("Freelancer Not Found");
      return GetFreelancerExperiencesResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    }
    Freelancer freelancer = freelancerOptional.get();
    List<ExperienceView> experiences = convertToExperienceViewList(freelancer.getExperiences());
    return GetFreelancerExperiencesResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withExperiences(experiences)
        .build();
  }

  public List<ExperienceView> convertToExperienceViewList(List<Experience> experiences) {
    List<ExperienceView> experienceViews = new ArrayList<>();
    for (Experience experience : experiences) {
      ExperienceView experienceView =
          ExperienceView.builder()
              .withCompany_name(experience.getCompany_name())
              .withJob_title(experience.getJob_title())
              .withEmployment_start(experience.getEmployment_start())
              .withEmployment_end(experience.getEmployment_end())
              .withExperience_description(experience.getExperience_description())
              .withCity(experience.getCity())
              .build();
      experienceViews.add(experienceView);
    }
    return experienceViews;
  }
}
