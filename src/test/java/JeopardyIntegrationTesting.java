import Jeopardy.controller.CSVInput;
import Jeopardy.controller.EventLogger;
import Jeopardy.controller.GameManager;
import Jeopardy.model.Category;
import Jeopardy.model.Player;
import Jeopardy.model.Question;
import Jeopardy.observer.LogEvent;
import Jeopardy.view.PlayersPanel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class JeopardyIntegrationTesting {
    private static final String TEST_CSV = "integration_test.csv";

    @BeforeAll
    public static void setupTestData() throws IOException {
        try (FileWriter writer = new FileWriter(TEST_CSV)) {
            writer.write("Category,Value,Question,OptionA,OptionB,OptionC,OptionD,CorrectAnswer\n");
            writer.write("Test,100,Q1?,A1,A2,A3,A4,A\n");
            writer.write("Test,200,Q2?,B1,B2,B3,B4,B\n");
            writer.write("Test,300,Q3?,C1,C2,C3,C4,C\n");
            writer.write("Test,400,Q4?,D1,D2,D3,D4,D\n");
            writer.write("Test,500,Q5?,E1,E2,E3,E4,A\n");
        }
    }

    @Test
    public void testCompleteGameFlow() {


        List<Category> categories = CSVInput.createCategories(TEST_CSV);
        assertNotNull(categories);
        assertEquals(1, categories.size());

        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "Player1"));
        players.add(new Player(2, "Player2"));

        
       
       
        //Initialize game manager
        GameManager gm = new GameManager();
        PlayersPanel playersPanel = new PlayersPanel(players);
        gm.addPlayers(players);
        gm.LinkObserver(playersPanel);

        // Simulate gameplay
        Player firstPlayer = gm.getCurrentPlayer();
        assertEquals("Player1", firstPlayer.getName());

        gm.UpdateScore(100);
        assertEquals(100, firstPlayer.getScore());

        Player secondPlayer = gm.getCurrentPlayer();
        assertEquals("Player2", secondPlayer.getName());
    }

    @Test
    public void testCategoryWithAllQuestions() {
        List<Category> categories = CSVInput.createCategories(TEST_CSV);
        Category category = categories.get(0);
        
        assertEquals(5, category.getQuestions().size());
        
        int[] expectedValues = {100, 200, 300, 400, 500};
        List<Question> questions = category.getQuestions();
        
        for (int i = 0; i < questions.size(); i++) {
            assertEquals(expectedValues[i], questions.get(i).getScore());
        }
    }

    @Test
    public void testPlayerScoreProgression() {
        List<Player> players = new ArrayList<>();
        Player player = new Player(1, "TestPlayer");
        players.add(player);

        GameManager gm = new GameManager();
        gm.addPlayers(players);

        
        // Simulate correct answers
        player.addToScore(100);
        player.addToScore(200);
        player.addToScore(300);

        assertEquals(600, player.getScore());

        // Simulate wrong answer
        player.addToScore(-400);
        assertEquals(200, player.getScore());
    }

    @Test
    public void testMultiplePlayersScoring() {
        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "Alice"));
        players.add(new Player(2, "Bob"));
        players.add(new Player(3, "Charlie"));

        GameManager gm = new GameManager();
        PlayersPanel playersPanel = new PlayersPanel(players);
        gm.addPlayers(players);
        gm.LinkObserver(playersPanel);

        
        // Simulate turns
        gm.UpdateScore(100);  // Alice
        gm.UpdateScore(200);  // Bob
        gm.UpdateScore(300);  // Charlie
        gm.UpdateScore(400);  // Alice

        assertEquals(500, players.get(0).getScore());
        assertEquals(200, players.get(1).getScore());
        assertEquals(300, players.get(2).getScore());
    }

    @Test
    public void testEventLoggingIntegration() {
        EventLogger logger = EventLogger.getEventLoggerInstance();
        
        LogEvent event = new LogEvent.Builder()
                .playerName("IntegrationPlayer")
                .activity("Integration Test")
                .category("Test Category")
                .questionValue(100)
                .answerGiven("A")
                .result("CORRECT")
                .scoreAfterPlay("100")
                .build();

        assertDoesNotThrow(() -> logger.logEvent(event));
    }
}