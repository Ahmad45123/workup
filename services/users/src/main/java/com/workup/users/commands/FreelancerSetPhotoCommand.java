package com.workup.users.commands;

import com.workup.shared.commands.users.requests.FreelancerSetPhotoRequest;
import com.workup.shared.commands.users.responses.FreelancerSetPhotoResponse;
import com.workup.shared.enums.HttpStatusCode;
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
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .build();
    }
    return FreelancerSetPhotoResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }
}
