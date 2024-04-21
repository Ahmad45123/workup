package com.workup.users.commands;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import com.workup.users.db.Client;

public class FreelancerSetPhotoCommand
        extends UserCommand<FreelancerSetPhotoRequest, FreelancerSetPhotoResponse> {

    @Override
    public FreelancerSetPhotoResponse Run(FreelancerSetPhotoRequest request) {

        String name = PHOTO_BUCKET + request.user_id;

        byte[] arr = Base64.getDecoder().decode(request.photo_encoded);

        try {
            // save arr to gridfs
            gridFsTemplate.store(new ByteArrayInputStream(arr), name);

            // bytesArr = gridFsTemplate.getResource(name).getInputStream().readAllBytes();

        } catch (Exception e) {
            return FreelancerSetPhotoResponse.builder()
                    .withSuccess(false)
                    .build();
        }
        return FreelancerSetPhotoResponse.builder()
                .build();

    }

}