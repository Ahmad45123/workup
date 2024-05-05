package com.workup.jobs;

import com.workup.shared.enums.ServiceQueueNames;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JobsApplication {

  public static void main(String[] args) {
    SpringApplication.run(JobsApplication.class, args);
  }

  @Bean
  public Queue myQueue() {
    return new Queue(ServiceQueueNames.JOBS);
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public ApplicationRunner runner() {
    return args -> {
      System.out.println("WE ARE NEW IN JOBS");
    };
  }
}
