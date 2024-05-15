package com.workup.webserver;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebserverApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebserverApplication.class, args);
  }

  // runner
  @Bean
  public ApplicationRunner runner() {
    return args -> {
      System.out.println("ApplicationRunner is executing");
    };
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
