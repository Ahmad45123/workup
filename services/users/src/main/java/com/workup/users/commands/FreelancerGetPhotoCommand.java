package com.workup.users.commands;

import java.util.Base64;
import java.util.Optional;

import com.workup.shared.commands.users.requests.FreelancerGetPhotoRequest;
import com.workup.shared.commands.users.responses.FreelancerGetPhotoResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Client;

public class FreelancerGetPhotoCommand
        extends UserCommand<FreelancerGetPhotoRequest, FreelancerGetPhotoResponse> {

    @Override
    public FreelancerGetPhotoResponse Run(FreelancerGetPhotoRequest request) {
        Optional<Client> clientOptional = clientRepository.findById(request.user_id);

        if (!clientOptional.isPresent()) {
            return FreelancerGetPhotoResponse.builder()
                    .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
                    .build();
        }
        String name = PHOTO_BUCKET + request.user_id;

        byte[] bytesArr;
        try {
            bytesArr = gridFsTemplate.getResource(name).getInputStream().readAllBytes();
        } catch (Exception e) {
            return FreelancerGetPhotoResponse.builder()
                    .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
                    .build();
        }

        String base64Encoded = Base64.getEncoder().encodeToString(bytesArr);

        return FreelancerGetPhotoResponse.builder()
                .withStatusCode(HttpStatusCode.OK)
                .withPhotoEncoded(base64Encoded)
                .build();

    }

}