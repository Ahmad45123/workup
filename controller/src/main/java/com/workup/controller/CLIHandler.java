package com.workup.controller;

import asg.cliche.CLIException;
import asg.cliche.Command;
import com.workup.shared.commands.controller.SetMaxThreadsRequest;
import com.workup.shared.enums.ControllerQueueNames;
import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class CLIHandler {
  @Autowired AmqpTemplate rabbitTemplate;
  private static final Map<String, String> appQueueMap = new HashMap<>();

  static {
    appQueueMap.put("jobs", ControllerQueueNames.JOBS);
    appQueueMap.put("users", ControllerQueueNames.USERS);
    appQueueMap.put("payments", ControllerQueueNames.PAYMENTS);
    appQueueMap.put("contracts", ControllerQueueNames.CONTRACTS);
  }

  @Command(description = "Set the maximum number of threads for a specific app")
  public String maxthreads(String app, int maxThreads) throws CLIException {
    app = app.toLowerCase();
    if (!appQueueMap.containsKey(app)) {
      return "Error: app can only be jobs, users, contracts or payments!";
    }
    if (maxThreads > 100 || maxThreads < 1) {
      return "Error: Max threads must have a value between 1 and 50";
    }
    rabbitTemplate.convertAndSend(
        appQueueMap.get(app),
        "",
        SetMaxThreadsRequest.builder().withMaxThreads(maxThreads).build());
    return "MaxThreads";
  }

  @Command(description = "Set the maximum number of DB connections for a specific app")
  public String maxdb(String app, int appNum, String maxDBConn) {
    return "maxdb";
  }

  @Command(description = "starts a specific app")
  public String start(String app, int appNum) {
    return "start";
  }

  @Command(description = "stops a specific app")
  public String freeze(String app, int appNum) {
    return "freeze";
  }

  @Command(description = "stops a specific app")
  public String setmq(String app, int appNum) {
    return "setmq";
  }

  @Command(description = "stops a specific app")
  public String setErrorReportingLevel(String app, int appNum) {
    return "error level";
  }

  @Command(description = "Creates a new command")
  public String addcommand(String app, String commandName, String className) {
    return "Add command";
  }

  @Command(description = "Updates an existing command")
  public String updatecommand(String app, String commandName, String className) {
    return "Update Command";
  }

  @Command(description = "Deletes an existing command")
  public String deletecommand(String app, String commandName, String className) {
    return "Delete command";
  }

  @Command(description = "stops a specific app")
  public String updateClass(String app, int appNum) {
    return "update class";
  }
}
