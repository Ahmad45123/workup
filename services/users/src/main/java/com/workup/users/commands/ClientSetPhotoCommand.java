package com.workup.users.commands;

import com.workup.shared.commands.users.requests.ClientSetPhotoRequest;
import com.workup.shared.commands.users.responses.ClientSetPhotoResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Client;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientSetPhotoCommand
    extends UserCommand<ClientSetPhotoRequest, ClientSetPhotoResponse> {
  private static final Logger logger = LogManager.getLogger(ClientSetPhotoCommand.class);

  @Override
  public ClientSetPhotoResponse Run(ClientSetPhotoRequest request) {
    logger.info("[i] Setting Photo for Client with id: " + request.getUserId());
    Optional<Client> clientOption = clientRepository.findById(request.getUserId());

    if (!clientOption.isPresent()) {
      logger.error("[x] Client Doesn't Exist");
      throw new RuntimeException("User not found");
    }

    Client client = clientOption.get();

    client.setPhoto_link(request.photoLink);

    clientRepository.save(client);

    return ClientSetPhotoResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }
}
