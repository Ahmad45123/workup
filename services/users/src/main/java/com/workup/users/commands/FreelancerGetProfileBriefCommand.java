package com.workup.users.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.workup.shared.commands.users.responses.FreelancerRegisterResponse;
import com.workup.users.db.Experience;
import com.workup.users.db.Freelancer;
import com.workup.users.repositories.FreelancerRepository;

public class FreelancerGetProfileBriefCommand
        extends UserCommand<FreelancerGetProfileBriefRequest, FreelancerGetProfileBriefResponse> {

    @Override
    public FreelancerGetProfileBriefResponse Run(FreelancerGetProfileBriefRequest request) {
        Optional<Freelancer> freelancer = freelancerRepository.findById(request.user_id);

        if (!freelancer.isPresent()) {
            return null;

        }

        return FreelancerGetProfileBriefResponse.builder()
                .withEmail(freelancer.get().getEmail())
                .withFull_name(freelancer.get().getFull_name())

                // .withBirth_date(freelancer.get().getBirthdate())
                // .withCity(freelancer.get().getCity()).withDescription(freelancer.get().getDescription())
                // .withEmail(freelancer.get().getEmail()).withFull_name(freelancer.get().getFull_name())
                // .withJob_title(freelancer.get().getJob_title()).withLanguages(freelancer.get().getLanguages())
                // .withSkills(freelancer.get().getSkills())
                .build();

    }

}