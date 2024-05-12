package com.workup.contracts;

import static com.workup.contracts.tests.InitiateContractTests.initiateContractTest1;

import com.workup.shared.enums.ServiceQueueNames;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.workup")
@EnableCaching
public class ContractsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ContractsApplication.class, args);
  }

  @Bean
  public ApplicationRunner runner(AmqpTemplate template) {
    return args -> {
      System.out.println("ApplicationRunner is executing");

      // Use below example function to test sending to the queue.
      initiateContractTest1(template);
    };
  }

  @Bean
  public Queue myQueue() {
    return new Queue(ServiceQueueNames.CONTRACTS);
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
