import Jeopardy.controller.EventLogger;
import Jeopardy.observer.LogEvent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class EventLoggerTesting {
    private EventLogger logger;
    private static final String LOG_FILE = "game_event_log.csv";

    @BeforeEach
    public void setUp() {
        logger = EventLogger.getEventLoggerInstance();
    }

    @Test
    public void testSingletonPattern() {
        EventLogger logger1 = EventLogger.getEventLoggerInstance();
        EventLogger logger2 = EventLogger.getEventLoggerInstance();
        assertSame(logger1, logger2);
    }

    @Test
    public void testLogFileCreation() {
        File logFile = new File(LOG_FILE);
        assertTrue(logFile.exists());
    }

    @Test
    public void testLogEvent() throws Exception {
        LogEvent event = new LogEvent.Builder()
                .playerName("TestPlayer")
                .activity("Test Activity")
                .build();

        logger.logEvent(event);

        File logFile = new File(LOG_FILE);
        assertTrue(logFile.exists());
        assertTrue(logFile.length() > 0);
    }

    @Test
    public void testLogFileHeaders() throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(LOG_FILE))) {
            String header = br.readLine();
            assertTrue(header.contains("Case_ID"));
            assertTrue(header.contains("Player_ID"));
            assertTrue(header.contains("Activity"));
            assertTrue(header.contains("Timestamp"));
        }
    }

    @Test
    public void testMultipleLogEvents() throws Exception {
        long initialSize = new File(LOG_FILE).length();

        for (int i = 0; i < 5; i++) {
            LogEvent event = new LogEvent.Builder()
                    .playerName("Player" + i)
                    .activity("Activity" + i)
                    .build();
            logger.logEvent(event);
        }

        long finalSize = new File(LOG_FILE).length();
        assertTrue(finalSize > initialSize);
    }
}
