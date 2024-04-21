package com.workup.users.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.workup.shared.commands.users.responses.FreelancerRegisterResponse;
import com.workup.users.db.Client;
import com.workup.users.db.Experience;
import com.workup.users.db.Freelancer;
import com.workup.users.repositories.FreelancerRepository;

public class ClientSetProfileCommand
        extends UserCommand<ClientSetProfileRequest, ClientSetProfileResponse> {

    @Override
    public ClientSetProfileResponse Run(ClientSetProfileRequest request) {

        Client client;

        if (request.user_id == null) {
            client = Client.builder().withId(null).build();
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

        return ClientSetProfileResponse.builder().withSuccess(true)
                .build();

    }

}