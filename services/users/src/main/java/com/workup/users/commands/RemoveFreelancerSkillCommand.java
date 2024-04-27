package com.workup.users.commands;

import com.workup.shared.commands.users.requests.RemoveFreelancerSkillRequest;
import com.workup.shared.commands.users.responses.RemoveFreelancerSkillResponse;
import com.workup.users.db.Freelancer;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class RemoveFreelancerSkillCommand extends UserCommand<RemoveFreelancerSkillRequest, RemoveFreelancerSkillResponse> {
    @Override
    public RemoveFreelancerSkillResponse Run(RemoveFreelancerSkillRequest request) {
        Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUser_id());
        if (freelancerOptional.isEmpty())
            return RemoveFreelancerSkillResponse.builder().withSuccess(false).build();
        Freelancer freelancer = freelancerOptional.get();
        freelancer.getSkills().remove(request.getSkillToRemove());
        freelancerRepository.save(freelancer);
        return RemoveFreelancerSkillResponse.builder().withSuccess(false).build();
    }
}
