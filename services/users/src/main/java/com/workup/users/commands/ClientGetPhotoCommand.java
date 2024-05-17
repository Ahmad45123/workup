package com.workup.users.commands;

import com.workup.shared.commands.users.requests.ClientGetPhotoRequest;
import com.workup.shared.commands.users.responses.ClientGetPhotoResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Client;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientGetPhotoCommand
    extends UserCommand<ClientGetPhotoRequest, ClientGetPhotoResponse> {
  private static final Logger logger = LogManager.getLogger(ClientGetPhotoCommand.class);

  @Override
  public ClientGetPhotoResponse Run(ClientGetPhotoRequest request) {
    logger.info("[i] Getting Photo for Client with id: " + request.getUserId());
    Optional<Client> clientOptional = clientRepository.findById(request.getUserId());

    if (!clientOptional.isPresent()) {
      logger.error("[x] Client Doesn't Exist");
      return ClientGetPhotoResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .build();
    }

    Client client = clientOptional.get();

    return ClientGetPhotoResponse.builder()
        .withStatusCode(HttpStatusCode.OK)
        .withPhotoLink(client.getPhoto_link())
        .build();
  }
}
