package com.workup.payments;

import com.workup.shared.enums.ServiceQueueNames;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfigBase {

  @Bean
  public Queue paymentsQueueMock() {
    return new Queue(ServiceQueueNames.PAYMENTS);
  }
}
