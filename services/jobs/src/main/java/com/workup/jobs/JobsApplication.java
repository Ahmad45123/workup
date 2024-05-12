package com.workup.jobs;

import com.workup.shared.enums.ControllerQueueNames;
import com.workup.shared.enums.ServiceQueueNames;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class JobsApplication {

  public static void main(String[] args) {
    SpringApplication.run(JobsApplication.class, args);
  }

  @Bean
  public Queue myQueue() {
    return new Queue(ServiceQueueNames.JOBS);
  }

  @Bean
  public Queue controllerQueue() {
    return new AnonymousQueue();
  }

  @Bean
  public FanoutExchange fanout() {
    return new FanoutExchange(ControllerQueueNames.JOBS);
  }

  @Bean
  public Binding fanoutBinding(FanoutExchange fanout, Queue controllerQueue) {
    return BindingBuilder.bind(controllerQueue).to(fanout);
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public ThreadPoolTaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(50);
    executor.setMaxPoolSize(50);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("jobs-");
    executor.initialize();
    return executor;
  }
}
