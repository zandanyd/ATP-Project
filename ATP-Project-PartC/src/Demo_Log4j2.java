
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class Demo_Log4j2 {

    private static final Logger LOGInfo = LogManager.getLogger();

    public static void main(String[] args) {
        //loggingWithLog4j2();
        demonstrateLoggingLevels();
    }

    /**
     * Demonstrate logging using Log4J.
     * Log4j2 logging levels: https://www.tutorialspoint.com/log4j/log4j_logging_levels.htm
     * A log request of level p in a logger with level q is enabled if p >= q. This rule is at the heart of log4j.
     * It assumes that levels are ordered.
     * For the standard levels, we have ALL < DEBUG < INFO < WARN < ERROR < FATAL < OFF.
     */
    private static void loggingWithLog4j2() {
        LOGInfo.info("SOME MESSAGE!");
    }

    private static void demonstrateLoggingLevels() {
        // org.apache.logging.log4j.core.config.Configurator;
        Configurator.setLevel("com.example.Foo", Level.DEBUG);

        // You can also set the root logger:
        Configurator.setRootLevel(Level.DEBUG);

        LOGInfo.info(String.format("Logging level: %s", LOGInfo.getLevel()));

        LOGInfo.trace("Trace Message!");
        LOGInfo.debug("Debug Message!");
        LOGInfo.info("Info Message!");
        LOGInfo.warn("Warn Message!");
        LOGInfo.error("Error Message!");
        LOGInfo.fatal("Fatal Message!");
    }
}


