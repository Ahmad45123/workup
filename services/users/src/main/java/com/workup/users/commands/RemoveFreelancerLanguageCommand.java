package com.workup.users.commands;

import com.workup.shared.commands.users.requests.RemoveFreelancerLanguageRequest;
import com.workup.shared.commands.users.responses.RemoveFreelancerLanguageResponse;
import com.workup.users.db.Freelancer;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class RemoveFreelancerLanguageCommand extends UserCommand<RemoveFreelancerLanguageRequest, RemoveFreelancerLanguageResponse> {
    @Override
    public RemoveFreelancerLanguageResponse Run(RemoveFreelancerLanguageRequest request) {
        Optional<Freelancer> freelancerOptional = freelancerRepository.findById(request.getUser_id());
        if (freelancerOptional.isEmpty())
            return RemoveFreelancerLanguageResponse.builder().withSuccess(false).build();
        Freelancer freelancer = freelancerOptional.get();
        freelancer.getLanguages().remove(request.getLanguageToRemove());
        freelancerRepository.save(freelancer);
        return RemoveFreelancerLanguageResponse.builder().withSuccess(true).build();
    }
}
