package com.workup.users.commands;

import com.workup.users.db.Client;
import java.util.Optional;

public class ClientSetProfileCommand
    extends UserCommand<ClientSetProfileRequest, ClientSetProfileResponse> {

  @Override
  public ClientSetProfileResponse Run(ClientSetProfileRequest request) {

    Client client;

    if (request.user_id == null) {
      client = Client.builder().build();
    } else {
      Optional<Client> clientOption = clientRepository.findById(request.user_id);
      if (!clientOption.isPresent()) {
        throw new RuntimeException("User not found");
      }
      client = clientOption.get();
    }

    if (request.name != null) {
      client.setClient_name(request.name);
    }
    if (request.email != null) {
      client.setEmail(request.email);
    }
    if (request.city != null) {
      client.setCity(request.city);
    }
    if (request.description != null) {
      client.setClient_description(request.description);
    }
    if (request.industry != null) {
      client.setIndustry(request.industry);
    }
    if (request.employee_count != null) {
      client.setEmployee_count(request.employee_count);
    }

    clientRepository.save(client);

    return ClientSetProfileResponse.builder().withSuccess(true).build();
  }
}
