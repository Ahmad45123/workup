package com.workup.users.commands;

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
      return FreelancerSetResumeResponse.builder().withSuccess(false).build();
    }
    return FreelancerSetResumeResponse.builder().withSuccess(true).build();
  }
}
