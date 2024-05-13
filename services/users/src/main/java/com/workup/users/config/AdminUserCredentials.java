package com.workup.users.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AdminUserCredentials {
  // autowire ADMIN_EMAIL and ADMIN_PASSWORD from environment variables
  @Value("${ADMIN_EMAIL}")
  String ADMIN_EMAIL;

  @Value("${ADMIN_PASSWORD}")
  String ADMIN_PASSWORD;

  @Value("${ADMIN_USERID}")
  String ADMIN_USERID;
}
