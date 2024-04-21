package com.workup.users.commands;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import com.workup.users.db.Client;

public class FreelancerGetResumeCommand
        extends UserCommand<FreelancerGetResumeRequest, FreelancerGetResumeResponse> {

    @Override
    public FreelancerGetResumeResponse Run(FreelancerGetResumeRequest request) {
        Optional<Client> clientOptional = clientRepository.findById(request.user_id);

        if (!clientOptional.isPresent()) {
            return FreelancerGetResumeResponse.builder()
                    .withSuccess(false)
                    .build();
        }
        String name = RESUME_BUCKET + request.user_id;

        byte[] bytesArr;
        try {
            bytesArr = gridFsTemplate.getResource(name).getInputStream().readAllBytes();
        } catch (Exception e) {
            return FreelancerGetResumeResponse.builder()
                    .withSuccess(false)
                    .build();
        }

        String base64Encoded = Base64.getEncoder().encodeToString(bytesArr);

        return FreelancerGetResumeResponse.builder()
                .withSuccess(true)
                .withResumeEncoded(base64Encoded)
                .build();

    }

}