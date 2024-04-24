package com.workup.contracts;

import static com.workup.contracts.tests.InitiateContractTests.initiateContractTest1;

import com.workup.contracts.tests.RequestContractTerminationTests;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
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
      RequestContractTerminationTests requestTerminationCommandTests = new RequestContractTerminationTests();
      requestTerminationCommandTests.contractNotFoundTest(template);
    };
  }

  @Bean
  public Queue myQueue() {
    return new Queue("contractsqueue");
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
