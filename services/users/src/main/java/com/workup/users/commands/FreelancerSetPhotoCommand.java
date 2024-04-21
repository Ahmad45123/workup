package com.workup.users.commands;

import java.io.ByteArrayInputStream;
import java.util.Base64;

public class FreelancerSetPhotoCommand
        extends UserCommand<FreelancerSetPhotoRequest, FreelancerSetPhotoResponse> {

    @Override
    public FreelancerSetPhotoResponse Run(FreelancerSetPhotoRequest request) {

        String name = PHOTO_BUCKET + request.user_id;

        byte[] photo_bytes_arr = Base64.getDecoder().decode(request.photo_encoded);

        try {
            gridFsTemplate.store(new ByteArrayInputStream(photo_bytes_arr), name);
        } catch (Exception e) {
            return FreelancerSetPhotoResponse.builder()
                    .withSuccess(false)
                    .build();
        }
        return FreelancerSetPhotoResponse.builder()
                .withSuccess(true)
                .build();

    }

}