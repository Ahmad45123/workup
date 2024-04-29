package com.workup.users.commands;

import com.workup.shared.commands.users.requests.GetFreelancerSkillsRequest;
import com.workup.shared.commands.users.responses.GetFreelancerSkillsResponse;
import com.workup.users.db.Freelancer;
import java.util.List;
import java.util.Optional;

public class GetFreelancerSkillsCommand
    extends UserCommand<GetFreelancerSkillsRequest, GetFreelancerSkillsResponse> {

  @Override
  public GetFreelancerSkillsResponse Run(GetFreelancerSkillsRequest request) {
    Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUser_id());
    if (freelancerOptional.isEmpty())
      return GetFreelancerSkillsResponse.builder().withSuccess(false).build();
    Freelancer freelancer = freelancerOptional.get();
    List<String> skills = freelancer.getSkills();
    return GetFreelancerSkillsResponse.builder().withSuccess(true).withSkills(skills).build();
  }
}
