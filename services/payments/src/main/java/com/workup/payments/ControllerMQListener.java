package com.workup.payments;

import com.workup.payments.commands.PaymentCommandMap;
import com.workup.shared.commands.controller.*;
import com.workup.shared.enums.ServiceQueueNames;
import com.workup.shared.enums.ThreadPoolSize;
import com.zaxxer.hikari.HikariDataSource;
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
  @Autowired public PaymentCommandMap commandMap;
  @Autowired public ThreadPoolTaskExecutor taskExecutor;
  @Autowired private ApplicationContext context;
  @Autowired private RabbitListenerEndpointRegistry registry;
  @Autowired private HikariDataSource hikariDataSource;

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

  @RabbitHandler
  public void receive(UpdateCommandRequest in) throws Exception {
    //    try {
    //      String className = commandMap.getCommand(in.getName()).getClass().getName();
    //      System.out.println("Updating command: " + in.getName());
    //      System.out.println("Class: " + className);
    //      Class newClass = new MyClassLoader().loadClass(in.getByteCode(), className);
    //      commandMap.replaceCommand(in.getName(), newClass);
    //    } catch (Exception e) {
    //      System.out.println(e.getMessage());
    //      e.printStackTrace();
    //    }
  }

  @RabbitHandler
  private void SetMaxDBConnections(SetMaxDBConnectionsRequest in) {
    try {
      if (hikariDataSource == null) {
        System.out.println("HikariDataSource is null");
        return;
      }
      System.out.println("Max DB connections is: " + hikariDataSource.getMaximumPoolSize());
      hikariDataSource.setMaximumPoolSize(in.getMaxDBConnections());
      System.out.println("Max DB connections set to: " + hikariDataSource.getMaximumPoolSize());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
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

class MyClassLoader extends ClassLoader {
  public Class<?> loadClass(byte[] byteCode, String className) {
    System.out.println("Loading class: " + className);
    return defineClass(className, byteCode, 0, byteCode.length);
  }
}
