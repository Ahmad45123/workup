package com.workup.users.commands;

import com.workup.shared.commands.users.requests.FreelancerGetResumeRequest;
import com.workup.shared.commands.users.responses.FreelancerGetResumeResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Client;
import java.util.Base64;
import java.util.Optional;

public class FreelancerGetResumeCommand
    extends UserCommand<FreelancerGetResumeRequest, FreelancerGetResumeResponse> {

  @Override
  public FreelancerGetResumeResponse Run(FreelancerGetResumeRequest request) {
    Optional<Client> clientOptional = clientRepository.findById(request.user_id);

    if (!clientOptional.isPresent()) {
      return FreelancerGetResumeResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .build();
    }
    String name = RESUME_BUCKET + request.user_id;

    byte[] bytesArr;
    try {
      bytesArr = gridFsTemplate.getResource(name).getInputStream().readAllBytes();
    } catch (Exception e) {
      return FreelancerGetResumeResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .build();
    }

    String base64Encoded = Base64.getEncoder().encodeToString(bytesArr);

    return FreelancerGetResumeResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withResumeEncoded(base64Encoded)
        .build();
  }
}
