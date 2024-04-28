package com.workup.users.commands;

import com.workup.users.commands.requests.GetFreelancerExperiencesRequest;
import com.workup.users.commands.responses.GetFreelancerExperiencesResponse;
import com.workup.users.db.Experience;
import com.workup.users.db.Freelancer;

import java.util.List;
import java.util.Optional;

public class GetFreelancerExperiencesCommand extends UserCommand<GetFreelancerExperiencesRequest, GetFreelancerExperiencesResponse> {

    @Override
    public GetFreelancerExperiencesResponse Run(GetFreelancerExperiencesRequest request) {
        Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getFreelancerId());
        if (freelancerOptional.isEmpty())
            return GetFreelancerExperiencesResponse.builder().withSuccess(false).build();
        Freelancer freelancer = freelancerOptional.get();
        List<Experience> experiences = freelancer.getExperiences();
        return GetFreelancerExperiencesResponse.builder().withSuccess(true).withExperiences(experiences).build();
    }
}
