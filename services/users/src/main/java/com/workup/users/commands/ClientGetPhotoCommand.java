package com.workup.users.commands;

import com.workup.shared.commands.users.requests.ClientGetPhotoRequest;
import com.workup.shared.commands.users.responses.ClientGetPhotoResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Client;
import java.util.Optional;

public class ClientGetPhotoCommand
    extends UserCommand<ClientGetPhotoRequest, ClientGetPhotoResponse> {

  @Override
  public ClientGetPhotoResponse Run(ClientGetPhotoRequest request) {
    Optional<Client> clientOptional = clientRepository.findById(request.getUserId());

    if (!clientOptional.isPresent()) {
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
