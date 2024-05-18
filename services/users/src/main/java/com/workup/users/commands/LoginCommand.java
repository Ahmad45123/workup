package com.workup.users.commands;

import com.workup.shared.commands.users.requests.LoginRequest;
import com.workup.shared.commands.users.responses.SignUpAndInResponse;
import com.workup.shared.enums.AdminUserCredentials;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.users.UserType;
import com.workup.users.commands.utils.PasswordHasher;
import com.workup.users.db.Client;
import com.workup.users.db.Freelancer;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginCommand extends UserCommand<LoginRequest, SignUpAndInResponse> {
  private static final Logger logger = LogManager.getLogger(LoginCommand.class);

  @Override
  public SignUpAndInResponse Run(LoginRequest request) {
    logger.info("[i] Logging in user with email: " + request.getEmail());
    String email = request.getEmail();
    String password = request.getPassword();
    try {
      if (email.equals(AdminUserCredentials.ADMIN_EMAIL)
          && password.equals(AdminUserCredentials.ADMIN_PASSWORD)) {
        return SignUpAndInResponse.builder()
            .withSuccess(true)
            .withUserName(AdminUserCredentials.ADMIN_EMAIL)
            .withUserId(AdminUserCredentials.ADMIN_ID)
            .withUserType(UserType.ADMIN)
            .withStatusCode(HttpStatusCode.OK)
            .build();
      }
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

      logger.error("[x] User not found or password incorrect");
      // return unauthorized
      return SignUpAndInResponse.builder()
          .withSuccess(false)
          .withStatusCode(HttpStatusCode.UNAUTHORIZED)
          .withErrorMessage("Invalid email or password")
          .build();
    } catch (Exception e) {
      logger.error("[x] Error Logging in user: " + e.getMessage());
      return SignUpAndInResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withSuccess(false)
          .build();
    }
  }
}
