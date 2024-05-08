package com.workup.webserver.controller;

import com.workup.webserver.config.JwtService;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
  @Autowired JwtService jwtService;

  @PostMapping("/login")
  public String postMethodName(@RequestBody UserLoginRequest request) {

    // use jwy service to authenticate user assume usernameand password is right

    String token = jwtService.generateToken(request.getUserName(), "123479797", "USER");

    return token;
  }
}

/** InnerUserController */
@Builder
@Jacksonized
@Getter
class UserLoginRequest {
  String userName;
  String password;
}
