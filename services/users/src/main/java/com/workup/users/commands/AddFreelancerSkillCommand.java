package com.workup.users.commands;

import com.workup.shared.commands.users.requests.AddFreelancerSkillRequest;
import com.workup.shared.commands.users.responses.AddFreelancerSkillResponse;
import com.workup.users.db.Freelancer;
import java.util.List;
import java.util.Optional;

public class AddFreelancerSkillCommand
    extends UserCommand<AddFreelancerSkillRequest, AddFreelancerSkillResponse> {

  @Override
  public AddFreelancerSkillResponse Run(AddFreelancerSkillRequest request) {
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUser_id());
    if (freelancerOptional.isEmpty())
      return AddFreelancerSkillResponse.builder().withSuccess(false).build();
    String newSkill = request.getNewSkill();
    Freelancer freelancer = freelancerOptional.get();
    List<String> skills = freelancer.getSkills();
    if (!skills.contains(newSkill)) skills.add(newSkill);
    freelancerRepository.save(freelancer);
    return AddFreelancerSkillResponse.builder().withSuccess(true).build();
  }
}
