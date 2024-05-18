package com.workup.jobs;

import com.workup.jobs.commands.JobCommand;
import com.workup.jobs.commands.JobCommandMap;
import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.controller.*;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.shared.enums.ThreadPoolSize;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "#{controllerQueue.name}", id = "#{controllerQueue.name}")
public class ControllerMQListener {
  @Autowired public JobCommandMap commandMap;
  @Autowired public ThreadPoolTaskExecutor taskExecutor;
  @Autowired private ApplicationContext context;
  @Autowired private RabbitListenerEndpointRegistry registry;

  private static final Logger logger = LogManager.getLogger(ControllerMQListener.class);

  @RabbitHandler
  public void receive(SetMaxThreadsRequest in) throws Exception {
    try {
      logger.info("Max threads is: " + taskExecutor.getMaxPoolSize());
      setThreads(in.getMaxThreads());
      ThreadPoolSize.POOL_SIZE = taskExecutor.getMaxPoolSize();
      logger.info("Max threads set to: " + taskExecutor.getMaxPoolSize());
    } catch (Exception e) {
      logger.info(e.getMessage());
      e.printStackTrace();
    }
  }

  @RabbitHandler
  public void receive(SetLoggingLevelRequest in) throws Exception {
    try {
      Logger logger = LogManager.getLogger("com.workup.jobs");
      Configurator.setAllLevels(logger.getName(), Level.valueOf(in.getLevel()));
      logger.info("Logging level set to: " + in.getLevel());
    } catch (Exception e) {
      logger.info(e.getMessage());
      e.printStackTrace();
    }
  }

  @RabbitHandler
  public void receive(FreezeRequest in) throws Exception {
    try {
      registry.getListenerContainer(ServiceQueueNames.PAYMENTS).stop();
      setThreads(1);
      logger.info("Stopped all threads.");
    } catch (Exception e) {
      logger.info(e.getMessage());
      e.printStackTrace();
    }
  }

  @RabbitHandler
  public void receive(ContinueRequest in) throws Exception {
    try {
      registry.getListenerContainer(ServiceQueueNames.PAYMENTS).start();
      setThreads(ThreadPoolSize.POOL_SIZE);
      logger.info("Continued all threads.");
    } catch (Exception e) {
      logger.info(e.getMessage());
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  @RabbitHandler
  public void receive(UpdateCommandRequest in) throws Exception {
    try {
      byte[] byteArray = in.getByteCode();
      Class<?> clazz =
          (Class<?>)
              (new MyClassLoader(this.getClass().getClassLoader())
                  .loadClass(byteArray, in.getClassName()));

      commandMap.replaceCommand(
          in.getCommandName(),
          (Class<? extends JobCommand<? extends CommandRequest, ? extends CommandResponse>>)
              ((Command<?, ?>) clazz.newInstance()).getClass());

      logger.info("Updated command: " + in.getCommandName());
      // clazz.newInstance().Run(null);
    } catch (Exception e) {
      logger.info(e.getMessage());
      e.printStackTrace();
    }
  }

  @RabbitHandler
  public void receive(DeleteCommandRequest in) throws Exception {
    try {
      commandMap.removeCommand(in.getCommandName());
      System.out.println("Deleted command: " + in.getCommandName());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  static class MyClassLoader extends ClassLoader {
    public MyClassLoader(ClassLoader classLoader) {
      super(classLoader);
    }

    public Class<?> loadClass(byte[] byteCode, String className) {
      return defineClass(className, byteCode, 0, byteCode.length);
    }
  }

  private void setThreads(int threads) throws NoSuchFieldException, IllegalAccessException {
    if (threads > taskExecutor.getCorePoolSize()) {
      taskExecutor.setMaxPoolSize(threads);
      taskExecutor.setCorePoolSize(threads);
    } else {
      taskExecutor.setCorePoolSize(threads);
      taskExecutor.setMaxPoolSize(threads);
    }
  }
}
