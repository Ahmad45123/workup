package com.workup.contracts;


import static com.workup.contracts.tests.InitiateContractTests.initiateContractTest1;
import com.workup.contracts.logger.ContractsLogger;
import com.workup.contracts.logger.LoggingLevel;
import com.workup.shared.enums.ControllerQueueNames;
import com.workup.shared.enums.ServiceQueueNames;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@ComponentScan(basePackages = "com.workup")
@EnableCaching
@EnableAsync
public class ContractsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ContractsApplication.class, args);
  }

  @Bean
  public ApplicationRunner runner(AmqpTemplate template) {
    return args -> {
      ContractsLogger.print("ApplicationRunner is executing", LoggingLevel.TRACE);

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

  @Bean
  public Queue controllerQueue() {
    return new AnonymousQueue();
  }

  @Bean
  public FanoutExchange fanout() {
    return new FanoutExchange(ControllerQueueNames.CONTRACTS);
  }

  @Bean
  public Binding fanoutBinding(FanoutExchange fanout, Queue controllerQueue) {
    return BindingBuilder.bind(controllerQueue).to(fanout);
  }

  @Bean
  public ThreadPoolTaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(50);
    executor.setMaxPoolSize(50);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("contracts-");
    executor.initialize();
    return executor;
  }
}
