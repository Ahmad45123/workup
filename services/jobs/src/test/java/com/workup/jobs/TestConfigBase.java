package com.workup.jobs;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfigBase {
  @Bean
  public Queue contractsQueueMock() {
    return new Queue("contractsqueue");
  }
}
