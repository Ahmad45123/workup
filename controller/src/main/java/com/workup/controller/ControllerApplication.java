package com.workup.controller;

import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import java.io.IOException;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ControllerApplication {

  public static void main(String[] args) throws IOException {
    SpringApplication.run(ControllerApplication.class, args);
  }

  @Bean
  public ApplicationRunner runner(CLIHandler cliHandler) {
    return args -> {
      Shell shell = ShellFactory.createConsoleShell("", "Controller", cliHandler);
      shell.commandLoop();
    };
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public CLIHandler cliHandler() {
    return new CLIHandler();
  }
}
