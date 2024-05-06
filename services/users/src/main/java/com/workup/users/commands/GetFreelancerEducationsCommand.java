package com.workup.users.commands;

import com.workup.shared.commands.users.requests.GetFreelancerEducationsRequest;
import com.workup.shared.commands.users.responses.GetFreelancerEducationsResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.views.users.EducationView;
import com.workup.users.db.Education;
import com.workup.users.db.Freelancer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GetFreelancerEducationsCommand
    extends UserCommand<GetFreelancerEducationsRequest, GetFreelancerEducationsResponse> {
  @Override
  public GetFreelancerEducationsResponse Run(GetFreelancerEducationsRequest request) {
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUser_id());
    if (freelancerOptional.isEmpty())
      return GetFreelancerEducationsResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    Freelancer freelancer = freelancerOptional.get();
    List<EducationView> educations = convertToEducationViewList(freelancer.getEducations());
    return GetFreelancerEducationsResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withEducations(educations)
        .build();
  }

  public List<EducationView> convertToEducationViewList(List<Education> educations) {
    List<EducationView> educationViews = new ArrayList<>();
    for (Education education : educations) {
      EducationView educationView =
          EducationView.builder()
              .withSchool_name(education.getSchool_name())
              .withDegree(education.getDegree())
              .withEducation_start_date(education.getEducation_start_date())
              .withCity(education.getCity())
              .withEnd_date(education.getEnd_date())
              .withMajor(education.getMajor())
              .withEducation_description(education.getEducation_description())
              .withGrade(education.getGrade())
              .build();
      educationViews.add(educationView);
    }
    return educationViews;
  }
}
