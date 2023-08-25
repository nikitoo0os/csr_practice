package com.vyatsu.practiceCSR.logger;

public class Logger {
    private static final Logger logger = LoggerFactory.getLogger(LoggerExample.class);

    public static void main(String[] args) {
        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");
    }
}
