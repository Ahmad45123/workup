package com.workup.users;

import com.workup.shared.enums.ControllerQueueNames;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.shared.enums.ThreadPoolSize;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
@EnableCaching
// @EnableMongoRepositories(basePackageClasses = ClientRepository.class)
public class UsersApplication {

  public static void main(String[] args) {

    SpringApplication.run(UsersApplication.class, args);
  }

  @Bean
  public Queue myQueue() {
    return new Queue(ServiceQueueNames.USERS);
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public Queue controllerQueue() {
    return new AnonymousQueue();
  }

  @Bean
  public FanoutExchange fanout() {
    return new FanoutExchange(ControllerQueueNames.USERS);
  }

  @Bean
  public Binding fanoutBinding(FanoutExchange fanout, Queue controllerQueue) {
    return BindingBuilder.bind(controllerQueue).to(fanout);
  }

  @Bean(name = "usersTaskExecutor")
  public ThreadPoolTaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(ThreadPoolSize.POOL_SIZE);
    executor.setMaxPoolSize(ThreadPoolSize.POOL_SIZE);
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("users-");
    executor.initialize();
    return executor;
  }
}
