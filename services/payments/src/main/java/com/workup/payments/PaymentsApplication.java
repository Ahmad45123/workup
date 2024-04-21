package com.workup.payments;

import com.workup.shared.commands.payments.paymentrequest.requests.CreatePaymentRequestRequest;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PaymentsApplication {

  public static void main(String[] args) {
    SpringApplication.run(PaymentsApplication.class, args);
  }

  @Bean
  public ApplicationRunner runner(AmqpTemplate template) {
    return args -> {
      CreatePaymentRequestRequest createPaymentRequest = CreatePaymentRequestRequest
        .builder()
        .withAmount(1200)
        .withDescription("Payment for services rendered")
        .withClientId("3")
        .withFreelancerId("4")
        .build();
      template.convertSendAndReceive("paymentsqueue", createPaymentRequest);
    };
  }

  @Bean
  public Queue myQueue() {
    return new Queue("paymentsqueue");
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
