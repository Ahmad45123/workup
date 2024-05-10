package com.workup.users.commands;

import static com.workup.users.commands.utils.PasswordHasher.hashPassword;

import com.workup.shared.commands.users.requests.ClientRegisterRequest;
import com.workup.shared.commands.users.responses.SignUpAndInResponse;
import com.workup.shared.enums.HttpStatusCode;
import com.workup.shared.enums.users.UserType;
import com.workup.users.db.Client;
import java.util.Objects;

public class ClientRegisterCommand extends UserCommand<ClientRegisterRequest, SignUpAndInResponse> {

  @Override
  public SignUpAndInResponse Run(ClientRegisterRequest request) {
    if (Objects.isNull(request.getEmail())
        || Objects.isNull(request.getPassword())
        || Objects.isNull(request.getClientName())) {
      return SignUpAndInResponse.builder()
          .withStatusCode(HttpStatusCode.BAD_REQUEST)
          .withSuccess(false)
          .build();
    }
    try {
      Client client =
          Client.builder()
              .withEmail(request.getEmail())
              .withPassword_hash(hashPassword(request.getPassword()))
              .withClient_name(request.getClientName())
              .withIndustry(request.getIndustry())
              .withClient_description(request.getDescription())
              .withEmployee_count(request.getEmployeeCount())
              .withCity(request.getCity())
              .build();
      Client savedClient = clientRepository.save(client);

      return SignUpAndInResponse.builder()
          .withSuccess(true)
          .withUserName(savedClient.getEmail())
          .withUserId(savedClient.getId().toString())
          .withUserType(UserType.CLIENT)
          .withStatusCode(HttpStatusCode.OK)
          .build();
    } catch (Exception e) {
      return SignUpAndInResponse.builder()
          .withStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR)
          .withSuccess(false)
          .build();
    }
  }
}
