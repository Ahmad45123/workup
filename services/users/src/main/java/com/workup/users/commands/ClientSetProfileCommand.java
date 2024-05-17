package com.workup.users.commands;

import com.workup.shared.commands.users.requests.ClientSetProfileRequest;
import com.workup.shared.commands.users.responses.ClientSetProfileResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Client;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientSetProfileCommand
    extends UserCommand<ClientSetProfileRequest, ClientSetProfileResponse> {
  private static final Logger logger = LogManager.getLogger(ClientSetProfileCommand.class);

  @Override
  public ClientSetProfileResponse Run(ClientSetProfileRequest request) {
    logger.info("[i] Setting Profile for Client with id: " + request.getUserId());
    Client client;

    if (request.getUserId() == null) {
      client = Client.builder().build();
    } else {
      Optional<Client> clientOption = clientRepository.findById(request.getUserId());
      if (!clientOption.isPresent()) {
        logger.error("[x] Client Doesn't Exist");
        throw new RuntimeException("User not found");
      }
      client = clientOption.get();
    }

    if (request.getName() != null) {
      client.setClient_name(request.getName());
    }
    if (request.getEmail() != null) {
      client.setEmail(request.getEmail());
    }
    if (request.getCity() != null) {
      client.setCity(request.getCity());
    }
    if (request.getDescription() != null) {
      client.setClient_description(request.getDescription());
    }
    if (request.getIndustry() != null) {
      client.setIndustry(request.getIndustry());
    }
    if (request.getEmployeeCount() != null) {
      client.setEmployee_count(request.getEmployeeCount());
    }

    clientRepository.save(client);

    return ClientSetProfileResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }
}
