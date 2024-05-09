package com.workup.users.commands;

import static com.workup.users.commands.utils.AuthTokenHandler.generateToken;
import static com.workup.users.commands.utils.PasswordHasher.hashPassword;

import com.workup.shared.commands.users.requests.FreelancerRegisterRequest;
import com.workup.shared.commands.users.responses.SignUpAndInResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Freelancer;
import java.util.Objects;

public class FreelancerRegister
    extends UserCommand<FreelancerRegisterRequest, SignUpAndInResponse> {

  @Override
  public SignUpAndInResponse Run(FreelancerRegisterRequest request) {
    if (Objects.isNull(request.getEmail())
        || Objects.isNull(request.getPassword())
        || Objects.isNull(request.getFullName())) {
      return SignUpAndInResponse.builder().withStatusCode(HttpStatusCode.BAD_REQUEST).build();
    }
    Freelancer freelancer =
        Freelancer.builder()
            .withEmail(request.getEmail())
            .withPassword_hash(hashPassword(request.getPassword()))
            .withFull_name(request.getFullName())
            .withJob_title(request.getJobTitle())
            .withCity(request.getCity())
            .build();

    return SignUpAndInResponse.builder()
        .withAuthToken(generateToken(freelancer.getEmail()))
        .withStatusCode(HttpStatusCode.OK)
        .build();
  }
}
