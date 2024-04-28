package com.workup.users.commands;

import com.workup.users.commands.requests.AddFreelancerExperienceRequest;
import com.workup.users.commands.responses.AddFreelancerExperienceResponse;
import com.workup.users.db.Experience;
import com.workup.users.db.Freelancer;

import java.util.Optional;

public class AddFreelancerExperienceCommand extends UserCommand<AddFreelancerExperienceRequest, AddFreelancerExperienceResponse> {
    @Override
    public AddFreelancerExperienceResponse Run(AddFreelancerExperienceRequest request) {
        Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getFreelancerId());
        if (freelancerOptional.isEmpty())
            return AddFreelancerExperienceResponse.builder().withSuccess(false).build();
        Freelancer freelancer = freelancerOptional.get();
        Experience newExperience = experienceRepository.save(request.getNewExperience());
        freelancer.getExperiences().add(newExperience);
        freelancerRepository.save(freelancer);
        return AddFreelancerExperienceResponse.builder().withSuccess(true).withFreelancer(freelancer).build();
    }
}
