package com.workup.users.commands;

import com.workup.users.commands.UserCommand;
import com.workup.users.commands.requests.UpdateFreelancerExperienceRequest;
import com.workup.users.commands.responses.UpdateFreelancerExperienceResponse;
import com.workup.users.db.Experience;
import com.workup.users.db.Freelancer;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class UpdateFreelancerExperienceCommand extends UserCommand<UpdateFreelancerExperienceRequest, UpdateFreelancerExperienceResponse> {
    @Override
    public UpdateFreelancerExperienceResponse Run(UpdateFreelancerExperienceRequest request) {
        Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getFreelancer_id());
        if (freelancerOptional.isEmpty())
            return UpdateFreelancerExperienceResponse.builder().withSuccess(false).build();
        Freelancer freelancer = freelancerOptional.get();
        updateExperience(request.getUpdatedExperience().getId().toString(), request.getUpdatedExperience());
        return UpdateFreelancerExperienceResponse.builder().withSuccess(true).withFreelancer(freelancer).build();
    }

    public ResponseEntity<Experience> updateExperience(String id, Experience updatedExperience) {
        Optional<Experience> experienceOptional = experienceRepository.findById(id);
        if (experienceOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Experience existingExperience = experienceOptional.get();
        BeanUtils.copyProperties(updatedExperience, existingExperience, "id");
        experienceRepository.save(existingExperience);
        return ResponseEntity.ok(existingExperience);
    }
}
