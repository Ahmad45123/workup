package com.workup.users.commands;

import com.workup.shared.commands.users.requests.ClientSetProfileRequest;
import com.workup.shared.commands.users.responses.ClientSetProfileResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Client;
import java.util.Optional;

public class ClientSetProfileCommand
    extends UserCommand<ClientSetProfileRequest, ClientSetProfileResponse> {

  @Override
  public ClientSetProfileResponse Run(ClientSetProfileRequest request) {

    Client client;

    if (request.getUser_id() == null) {
      client = Client.builder().build();
    } else {
      Optional<Client> clientOption = clientRepository.findById(request.getUser_id());
      if (!clientOption.isPresent()) {
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
    if (request.getEmployee_count() != null) {
      client.setEmployee_count(request.getEmployee_count());
    }

    clientRepository.save(client);

    return ClientSetProfileResponse.builder().withStatusCode(HttpStatusCode.OK).build();
  }
}
