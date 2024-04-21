package com.workup.users.commands;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import com.workup.users.db.Client;

public class FreelancerGetPhotoCommand
        extends UserCommand<FreelancerGetPhotoRequest, FreelancerGetPhotoResponse> {


    @Override
    public FreelancerGetPhotoResponse Run(FreelancerGetPhotoRequest request) {
        Optional<Client> clientOptional = clientRepository.findById(request.user_id);

        if (!clientOptional.isPresent()) {
            return FreelancerGetPhotoResponse.builder()
                    .withSuccess(false)
                    .build();
        }
        String name = PHOTO_BUCKET + request.user_id;

        byte[] bytesArr;
        try {
            bytesArr = gridFsTemplate.getResource(name).getInputStream().readAllBytes();
        } catch (Exception e) {
            return FreelancerGetPhotoResponse.builder()
                    .withSuccess(false)
                    .build();
        }

        String base64Encoded = Base64.getEncoder().encodeToString(bytesArr);

        return FreelancerGetPhotoResponse.builder()
                .withPhotoEncoded(base64Encoded)
                .build();

    }

}