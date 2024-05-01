package com.workup.users.commands;

import com.workup.shared.commands.users.requests.FreelancerSetResumeRequest;
import com.workup.shared.commands.users.responses.FreelancerSetResumeResponse;
import com.workup.shared.enums.HttpStatusCode;
import java.io.ByteArrayInputStream;
import java.util.Base64;

public class FreelancerSetResumeCommand
    extends UserCommand<FreelancerSetResumeRequest, FreelancerSetResumeResponse> {

  @Override
  public FreelancerSetResumeResponse Run(FreelancerSetResumeRequest request) {

    String name = RESUME_BUCKET + request.user_id;

    byte[] resume_byes_arr = Base64.getDecoder().decode(request.resume_encoded);

    try {
      gridFsTemplate.store(new ByteArrayInputStream(resume_byes_arr), name);
    } catch (Exception e) {
      return FreelancerSetResumeResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .build();
    }
    return FreelancerSetResumeResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }
}
