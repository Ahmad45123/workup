package com.workup.users.commands;

import com.workup.shared.commands.users.requests.ClientSetPhotoRequest;
import com.workup.shared.commands.users.responses.ClientSetPhotoResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Client;
import java.util.Optional;

public class ClientSetPhotoCommand
    extends UserCommand<ClientSetPhotoRequest, ClientSetPhotoResponse> {

  @Override
  public ClientSetPhotoResponse Run(ClientSetPhotoRequest request) {

    Optional<Client> clientOption = clientRepository.findById(request.user_id);

    if (!clientOption.isPresent()) {
      throw new RuntimeException("User not found");
    }

    Client client = clientOption.get();

    client.setPhoto_link(request.photoLink);

    clientRepository.save(client);

    return ClientSetPhotoResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }
}
