package com.workup.users.commands;

import com.workup.shared.commands.users.requests.ClientGetProfileRequest;
import com.workup.shared.commands.users.responses.ClientGetProfileResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Client;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientGetProfileCommand
    extends UserCommand<ClientGetProfileRequest, ClientGetProfileResponse> {
  private static final Logger logger = LogManager.getLogger(ClientGetProfileCommand.class);

  @Override
  public ClientGetProfileResponse Run(ClientGetProfileRequest request) {
    logger.info("[i] Getting Profile for Client with id: " + request.getUserId());
    Optional<Client> clientOptional = clientRepository.findById(request.getUserId());

    if (!clientOptional.isPresent()) {
      logger.error("[x] Client Doesn't Exist");
      return ClientGetProfileResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .build();
    }

    Client client = clientOptional.get();

    return ClientGetProfileResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withName(client.getClient_name())
        .withEmail(client.getEmail())
        .withCity(client.getCity())
        .withDescription(client.getClient_description())
        .withIndustry(client.getIndustry())
        .withEmployeeCount(client.getEmployee_count())
        .build();
  }
}
