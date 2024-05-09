package com.workup.users.commands;

import com.workup.shared.commands.users.requests.LoginRequest;
import com.workup.shared.commands.users.responses.SignUpAndInResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.users.UserType;
import com.workup.users.commands.utils.PasswordHasher;
import com.workup.users.db.Client;
import com.workup.users.db.Freelancer;
import java.util.Optional;

public class LoginCommand extends UserCommand<LoginRequest, SignUpAndInResponse> {

  @Override
  public SignUpAndInResponse Run(LoginRequest request) {
    String email = request.getEmail();
    String password = request.getPassword();
    try {
      Optional<Client> client = clientRepository.findByEmail(email);
      if (client.isPresent()) {
        if (PasswordHasher.checkPassword(password, client.get().getPassword_hash())) {
          return SignUpAndInResponse.builder()
              .withSuccess(true)
              .withUserName(client.get().getEmail())
              .withUserId(client.get().getId().toString())
              .withUserType(UserType.CLIENT)
              .withStatusCode(HttpStatusCode.OK)
              .build();
        }
      }
      // check if freelancer
      Optional<Freelancer> freelancer = freelancerRepository.findByEmail(email);
      if (freelancer.isPresent()) {
        if (PasswordHasher.checkPassword(password, freelancer.get().getPassword_hash())) {
          return SignUpAndInResponse.builder()
              .withSuccess(true)
              .withUserName(freelancer.get().getEmail())
              .withUserId(freelancer.get().getId().toString())
              .withUserType(UserType.FREELANCER)
              .withStatusCode(HttpStatusCode.OK)
              .build();
        }
      }

      // return unauthorized
      return SignUpAndInResponse.builder()
          .withSuccess(false)
          .withStatusCode(HttpStatusCode.UNAUTHORIZED)
          .build();
    } catch (Exception e) {
      return SignUpAndInResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withSuccess(false)
          .build();
    }
  }
}
