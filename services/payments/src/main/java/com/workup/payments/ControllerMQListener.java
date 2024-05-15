package com.workup.payments;

import com.workup.shared.commands.controller.SetMaxThreadsRequest;
import java.lang.reflect.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "#{controllerQueue.name}")
public class ControllerMQListener {

  private static final Logger logger = LogManager.getLogger(ControllerMQListener.class);

  @Autowired public ThreadPoolTaskExecutor taskExecutor;

  @Autowired private ApplicationContext context;

  @RabbitHandler
  public void receive(SetMaxThreadsRequest in) throws Exception {
    try {
      ThreadPoolTaskExecutor myBean = context.getBean(ThreadPoolTaskExecutor.class);
      Field maxPoolSize = ThreadPoolTaskExecutor.class.getDeclaredField("maxPoolSize");
      maxPoolSize.setAccessible(true);
      maxPoolSize.set(myBean, in.getMaxThreads());
      Field corePoolSize = ThreadPoolTaskExecutor.class.getDeclaredField("corePoolSize");
      corePoolSize.setAccessible(true);
      corePoolSize.set(myBean, in.getMaxThreads());
    } catch (Exception e) {
      logger.error("Error setting max threads", e.getMessage());
    }
  }
}