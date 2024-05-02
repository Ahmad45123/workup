package com.workup.users;

import com.workup.shared.enums.ServiceQueueNames;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
// @EnableMongoRepositories(basePackageClasses = ClientRepository.class)
public class UsersApplication {

  public static void main(String[] args) {

    SpringApplication.run(UsersApplication.class, args);

    // @Bean
    // public MessageConverter messageConverter() {
    // return new Jackson2JsonMessageConverter();

  }

  @Bean
  public Queue myQueue() {
    return new Queue(ServiceQueueNames.USERS);
  }
}
