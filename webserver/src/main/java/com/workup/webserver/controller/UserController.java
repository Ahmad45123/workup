package com.workup.webserver.controller;

import com.workup.shared.commands.users.requests.ClientRegisterRequest;
import com.workup.shared.commands.users.requests.FreelancerRegisterRequest;
import com.workup.shared.commands.users.requests.LoginRequest;
import com.workup.shared.commands.users.responses.SignUpAndInResponse;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.webserver.config.JwtService;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
  @Autowired JwtService jwtService;
  @Autowired AmqpTemplate rabbitTemplate;

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

    // use jwy service to authenticate user assume usernameand password is right
    SignUpAndInResponse response =
        (SignUpAndInResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.USERS, request);
    if (response.isSuccess()) {
      String token =
          jwtService.generateToken(
              response.getUserName(), response.getUserId(), response.getUserType().toString());
      return ResponseEntity.ok(AuthResponse.builder().authToken(token).build());
    }
    return ResponseEntity.status(response.getStatusCode().getValue())
        .body(AuthResponse.builder().errorMessage(response.getErrorMessage()).build());
  }

  @PostMapping("/clients/register")
  public ResponseEntity<AuthResponse> registerAsClient(@RequestBody ClientRegisterRequest request) {

    // use jwy service to authenticate user assume usernameand password is right
    SignUpAndInResponse response =
        (SignUpAndInResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.USERS, request);
    if (response.isSuccess()) {
      String token =
          jwtService.generateToken(
              response.getUserName(), response.getUserId(), response.getUserType().toString());
      return ResponseEntity.ok(AuthResponse.builder().authToken(token).build());
    }
    return ResponseEntity.status(response.getStatusCode().getValue())
        .body(AuthResponse.builder().errorMessage(response.getErrorMessage()).build());
  }

  @PostMapping("/freelancers/register")
  public ResponseEntity<AuthResponse> postMethodName(
      @RequestBody FreelancerRegisterRequest request) {
    // use jwy service to authenticate user assume usernameand password is right
    SignUpAndInResponse response =
        (SignUpAndInResponse)
            rabbitTemplate.convertSendAndReceive(ServiceQueueNames.USERS, request);
    if (response.isSuccess()) {
      String token =
          jwtService.generateToken(
              response.getUserName(), response.getUserId(), response.getUserType().toString());
      return ResponseEntity.ok(AuthResponse.builder().authToken(token).build());
    }
    return ResponseEntity.status(response.getStatusCode().getValue())
        .body(AuthResponse.builder().errorMessage(response.getErrorMessage()).build());
  }
}

/** InnerUserController */
@Builder
@Jacksonized
@Getter
class AuthResponse {
  String authToken;
  String errorMessage;
}
