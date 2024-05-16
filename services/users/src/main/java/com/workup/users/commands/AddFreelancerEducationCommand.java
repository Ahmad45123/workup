package com.workup.users.commands;

import com.workup.shared.commands.users.requests.AddFreelancerEducationRequest;
import com.workup.shared.commands.users.responses.AddFreelancerEducationResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Education;
import com.workup.users.db.Freelancer;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddFreelancerEducationCommand
    extends UserCommand<AddFreelancerEducationRequest, AddFreelancerEducationResponse> {
  private static final Logger logger = LogManager.getLogger(AddFreelancerEducationCommand.class);

  @Override
  public AddFreelancerEducationResponse Run(AddFreelancerEducationRequest request) {
    logger.info("Add Freelancer Education");
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());
    if (freelancerOptional.isEmpty()) {
      logger.info("Freelancer Not Found");
      return AddFreelancerEducationResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    }
    Freelancer freelancer = freelancerOptional.get();
    Education newEducation =
        Education.builder()
            .withCity(request.getCity())
            .withDegree(request.getDegree())
            .withEducation_description(request.getEducation_description())
            .withEducation_start_date(request.getEducation_start_date())
            .withEnd_date(request.getEnd_date())
            .withGrade(request.getGrade())
            .withMajor(request.getMajor())
            .withSchool_name(request.getSchool_name())
            .build();
    newEducation = educationRepository.save(newEducation);
    freelancer.getEducations().add(newEducation);
    freelancerRepository.save(freelancer);
    return AddFreelancerEducationResponse.builder().withStatusCode(HttpStatusCode.CREATED).build();
  }
}
