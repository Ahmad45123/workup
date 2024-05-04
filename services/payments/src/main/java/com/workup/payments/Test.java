package com.workup.payments;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Test {
  private static final Logger logger = LogManager.getLogger(Test.class);

  public static void main(String[] args) {
    logger.info("Info level log message: Greeting endpoint was called");
    logger.debug("Debug level log message: This shows more detailed information");
    logger.info("Info level log message: Greeting endpoint was called");
    logger.error("Error level log message: This shows more detailed information");

    System.out.println("Hello");
  }
}
