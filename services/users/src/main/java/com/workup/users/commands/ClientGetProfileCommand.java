package com.workup.users.commands;

import java.util.Optional;

import com.workup.shared.commands.users.requests.ClientGetProfileRequest;
import com.workup.shared.commands.users.responses.ClientGetProfileResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.users.db.Client;

public class ClientGetProfileCommand
        extends UserCommand<ClientGetProfileRequest, ClientGetProfileResponse> {

    @Override
    public ClientGetProfileResponse Run(ClientGetProfileRequest request) {
        Optional<Client> clientOptional = clientRepository.findById(request.user_id);

        if (!clientOptional.isPresent()) {
            return ClientGetProfileResponse.builder().withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR).build();
        }

        Client client = clientOptional.get();

        return ClientGetProfileResponse.builder()
                .withStatusCode(HttpStatusCode.OK)
                .withName(client.getClient_name())
                .withEmail(client.getEmail())
                .withCity(client.getCity())
                .withDescription(client.getClient_description())
                .withIndustry(client.getIndustry())
                .withEmployee_count(client.getEmployee_count())
                .build();

    }

}