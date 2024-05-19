package com.workup.contracts.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class ContractsLogger {
  private static final Logger logger = LogManager.getLogger(ContractsLogger.class);

  public static void print(String logMessage, LoggingLevel level) {
    Configurator.setRootLevel(Level.TRACE);

    switch (level) {
      case TRACE -> logger.trace("Trace level log message: " + logMessage);
      case DEBUG -> logger.debug("Debug level log message: " + logMessage);
      case INFO -> logger.info("Info level log message: " + logMessage);
      case WARN -> logger.warn("Warn level log message: " + logMessage);
      case ERROR -> logger.error("Error level log message: " + logMessage);
    }
  }
}
