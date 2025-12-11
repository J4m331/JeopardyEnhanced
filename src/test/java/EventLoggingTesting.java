import org.junit.jupiter.api.Test;

import Jeopardy.observer.LogEvent;

import static org.junit.jupiter.api.Assertions.*;

public class EventLoggingTesting {
    @Test
    public void testBuilderPattern() {
        LogEvent event = new LogEvent.Builder()
                .playerName("TestPlayer")
                .activity("Test Activity")
                .category("Test Category")
                .questionValue(100)
                .answerGiven("A")
                .result("CORRECT")
                .scoreAfterPlay("100")
                .build();

        assertEquals("TestPlayer", event.getPlayerName());
        assertEquals("Test Activity", event.getActivity());
        assertEquals("Test Category", event.getCategory());
        assertEquals(100, event.getQuestionValue());
        assertEquals("A", event.getAnswerGiven());
        assertEquals("CORRECT", event.getResult());
        assertEquals("100", event.getScoreAfterPlay());
    }

    @Test
    public void testBuilderWithPartialData() {
        LogEvent event = new LogEvent.Builder()
                .playerName("Player1")
                .activity("Start Game")
                .build();

        assertEquals("Player1", event.getPlayerName());
        assertEquals("Start Game", event.getActivity());
        assertNull(event.getCategory());
        assertEquals(0, event.getQuestionValue());
    }

    @Test
    public void testMultipleBuilds() {
        LogEvent event1 = new LogEvent.Builder()
                .playerName("Player1")
                .result("CORRECT")
                .build();

        LogEvent event2 = new LogEvent.Builder()
                .playerName("Player2")
                .result("INCORRECT")
                .build();

        assertNotEquals(event1.getPlayerName(), event2.getPlayerName());
        assertNotEquals(event1.getResult(), event2.getResult());
    }
}