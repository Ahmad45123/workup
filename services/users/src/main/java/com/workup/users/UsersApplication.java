package com.workup.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @EnableMongoRepositories(basePackageClasses = ClientRepository.class)
public class UsersApplication {

  public static void main(String[] args) {

    SpringApplication.run(UsersApplication.class, args);
  }
}
