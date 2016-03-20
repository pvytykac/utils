import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{24} - %msg%n"
    }
}

root(WARN, ["STDOUT"])
logger("org.pvytykac", TRACE, ["STDOUT"], false)