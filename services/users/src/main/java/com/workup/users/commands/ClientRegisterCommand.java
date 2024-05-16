package com.workup.users.commands;

import static com.workup.users.commands.utils.PasswordHasher.hashPassword;

import com.workup.shared.commands.users.requests.ClientRegisterRequest;
import com.workup.shared.commands.users.responses.SignUpAndInResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.users.UserType;
import com.workup.users.db.Client;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientRegisterCommand extends UserCommand<ClientRegisterRequest, SignUpAndInResponse> {
  private static final Logger logger = LogManager.getLogger(ClientRegisterCommand.class);

  @Override
  public SignUpAndInResponse Run(ClientRegisterRequest request) {
    logger.info("[i] Registering Client with Email: " + request.getEmail());
    if (Objects.isNull(request.getEmail())
        || Objects.isNull(request.getPassword())
        || Objects.isNull(request.getClientName())) {
      logger.error("[x] Missing Required Fields");
      return SignUpAndInResponse.builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withSuccess(false)
          .build();
    }
    try {
      Client client =
          Client.builder()
              .withEmail(request.getEmail())
              .withPassword_hash(hashPassword(request.getPassword()))
              .withClient_name(request.getClientName())
              .withIndustry(request.getIndustry())
              .withClient_description(request.getDescription())
              .withEmployee_count(request.getEmployeeCount())
              .withCity(request.getCity())
              .build();
      Client savedClient = clientRepository.save(client);

      return SignUpAndInResponse.builder()
          .withSuccess(true)
          .withUserName(savedClient.getEmail())
          .withUserId(savedClient.getId().toString())
          .withUserType(UserType.CLIENT)
          .withStatusCode(HttpStatusCode.OK)
          .build();
    } catch (Exception e) {
      logger.error("[x] Error Registering Client: " + e.getMessage());
      return SignUpAndInResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withSuccess(false)
          .build();
    }
  }
}
