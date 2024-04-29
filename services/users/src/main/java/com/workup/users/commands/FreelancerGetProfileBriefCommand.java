package com.workup.users.commands;

import java.util.Optional;

import com.workup.shared.commands.users.requests.FreelancerGetProfileBriefRequest;
import com.workup.shared.commands.users.responses.FreelancerGetProfileBriefResponse;
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
                .withSuccess(true)
                .withEmail(freelancer.get().getEmail())
                .withFull_name(freelancer.get().getFull_name())
                .build();

    }

}