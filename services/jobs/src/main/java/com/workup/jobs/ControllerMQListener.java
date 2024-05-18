package com.workup.jobs;

import com.workup.jobs.commands.JobCommand;
import com.workup.jobs.commands.JobCommandMap;
import com.workup.shared.commands.Command;
import com.workup.shared.commands.CommandRequest;
import com.workup.shared.commands.CommandResponse;
import com.workup.shared.commands.controller.*;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.shared.enums.ThreadPoolSize;
import java.lang.reflect.Field;
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

  @RabbitHandler
  public void receive(SetMaxThreadsRequest in) throws Exception {
    try {
      System.out.println("Max threads is: " + taskExecutor.getMaxPoolSize());
      setThreads(in.getMaxThreads());
      ThreadPoolSize.POOL_SIZE = taskExecutor.getMaxPoolSize();
      System.out.println("Max threads set to: " + taskExecutor.getMaxPoolSize());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  @RabbitHandler
  public void receive(SetLoggingLevelRequest in) throws Exception {
    try {
      Logger logger = LogManager.getRootLogger();
      Configurator.setAllLevels(logger.getName(), Level.valueOf(in.getLevel()));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  @RabbitHandler
  public void receive(FreezeRequest in) throws Exception {
    try {
      registry.getListenerContainer(ServiceQueueNames.JOBS).stop();
      taskExecutor.shutdown();
      setThreads(0);
      System.out.println("Stopped all threads.");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  @RabbitHandler
  public void receive(ContinueRequest in) throws Exception {
    try {
      taskExecutor.start();
      setThreads(ThreadPoolSize.POOL_SIZE);
      registry.getListenerContainer(ServiceQueueNames.JOBS).start();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  @RabbitHandler
  public void receive(UpdateCommandRequest in) throws Exception {
    try {
      String className = commandMap.getCommand(in.getCommandName()).getClass().getName();
      byte[] byteArray = in.getByteCode();
      Class<?> clazz =
          (Class<?>)
              (new MyClassLoader(this.getClass().getClassLoader()).loadClass(byteArray, className));

      commandMap.replaceCommand(
          in.getCommandName(),
          (Class<? extends JobCommand<? extends CommandRequest, ? extends CommandResponse>>)
              ((Command<?, ?>) clazz.newInstance()).getClass());

      System.out.println("Updated command: " + in.getCommandName());
      // clazz.newInstance().Run(null);
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
    ThreadPoolTaskExecutor myBean = context.getBean(ThreadPoolTaskExecutor.class);
    Field maxPoolSize = ThreadPoolTaskExecutor.class.getDeclaredField("maxPoolSize");
    maxPoolSize.setAccessible(true);
    maxPoolSize.set(myBean, threads);
    Field corePoolSize = ThreadPoolTaskExecutor.class.getDeclaredField("corePoolSize");
    corePoolSize.setAccessible(true);
    corePoolSize.set(myBean, threads);
  }
}
