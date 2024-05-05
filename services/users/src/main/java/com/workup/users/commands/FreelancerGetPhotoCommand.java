package com.workup.users.commands;

import com.workup.shared.commands.users.requests.FreelancerGetPhotoRequest;
import com.workup.shared.commands.users.responses.FreelancerGetPhotoResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Client;
import java.util.Base64;
import java.util.Optional;

public class FreelancerGetPhotoCommand
    extends UserCommand<FreelancerGetPhotoRequest, FreelancerGetPhotoResponse> {

  @Override
  public FreelancerGetPhotoResponse Run(FreelancerGetPhotoRequest request) {
    Optional<Client> clientOptional = clientRepository.findById(request.getUser_id());

    if (!clientOptional.isPresent()) {
      return FreelancerGetPhotoResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .build();
    }
    String name = PHOTO_BUCKET + request.getUser_id();

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
