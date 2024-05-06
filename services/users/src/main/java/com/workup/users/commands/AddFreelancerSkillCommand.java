package com.workup.users.commands;

import com.workup.shared.commands.users.requests.AddFreelancerSkillRequest;
import com.workup.shared.commands.users.responses.AddFreelancerSkillResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddFreelancerSkillCommand
    extends UserCommand<AddFreelancerSkillRequest, AddFreelancerSkillResponse> {

  @Override
  public AddFreelancerSkillResponse Run(AddFreelancerSkillRequest request) {
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUserId());
    if (freelancerOptional.isEmpty())
      return AddFreelancerSkillResponse.builder()
          .withStatusCode(HttpStatusCode.NOT_FOUND)
          .withErrorMessage("Freelancer Doesn't Exist")
          .build();
    String newSkill = request.getNewSkill();
    Freelancer freelancer = freelancerOptional.get();
    if(freelancer.getSkills() == null) freelancer.setSkills(new ArrayList<>());
    List<String> skills = freelancer.getSkills();
    if (!skills.contains(newSkill)) skills.add(newSkill);
    freelancerRepository.save(freelancer);
    return AddFreelancerSkillResponse.builder().withStatusCode(HttpStatusCode.CREATED).build();
  }
}
