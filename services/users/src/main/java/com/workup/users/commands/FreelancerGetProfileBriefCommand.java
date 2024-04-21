package com.workup.users.commands;

import java.util.Optional;

import com.workup.users.db.Freelancer;

public class FreelancerGetProfileBriefCommand
        extends UserCommand<FreelancerGetProfileBriefRequest, FreelancerGetProfileBriefResponse> {

    @Override
    public FreelancerGetProfileBriefResponse Run(FreelancerGetProfileBriefRequest request) {
        Optional<Freelancer> freelancer = freelancerRepository.findById(request.user_id);

        if (!freelancer.isPresent()) {
            return FreelancerGetProfileBriefResponse.builder()
                    .withSuccess(false)
                    .build();

        }

        return FreelancerGetProfileBriefResponse.builder()
                .withEmail(freelancer.get().getEmail())
                .withFull_name(freelancer.get().getFull_name())
                .build();

    }

}