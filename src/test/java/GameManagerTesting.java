import Jeopardy.controller.GameManager;
import Jeopardy.model.Player;
import Jeopardy.view.PlayersPanel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class GameManagerTesting {
    private GameManager gameManager;
    private List<Player> players;
    private PlayersPanel playersPanel;

    @BeforeEach
    public void setUp() {
        gameManager = new GameManager();
        players = new ArrayList<>();
        players.add(new Player(1, "Alice"));
        players.add(new Player(2, "Bob"));
        players.add(new Player(3, "Charlie"));
        playersPanel = new PlayersPanel(players);
        gameManager.LinkObserver(playersPanel);
    }

    @Test
    public void testAddPlayers() {
        gameManager.addPlayers(players);
        assertEquals(3, gameManager.getPlayers().size());
        assertEquals("Alice", gameManager.getCurrentPlayer().getName());
    }

    @Test
    public void testUpdateScore() {
        gameManager.addPlayers(players);
        Player firstPlayer = gameManager.getCurrentPlayer();
        
        gameManager.UpdateScore(100);
        
        assertEquals(100, firstPlayer.getScore());
        assertNotEquals(firstPlayer, gameManager.getCurrentPlayer());
    }

    @Test
    public void testShiftPlayer() {
        gameManager.addPlayers(players);
        
        assertEquals("Alice", gameManager.getCurrentPlayer().getName());
        gameManager.shiftPlayer();
        assertEquals("Bob", gameManager.getCurrentPlayer().getName());
        gameManager.shiftPlayer();
        assertEquals("Charlie", gameManager.getCurrentPlayer().getName());
        gameManager.shiftPlayer();
        assertEquals("Alice", gameManager.getCurrentPlayer().getName());
    }

    @Test
    public void testPlayerRotation() {
        gameManager.addPlayers(players);
        
        for (int i = 0; i < 10; i++) {
            gameManager.shiftPlayer();
        }
        
        assertEquals("Bob", gameManager.getCurrentPlayer().getName());
    }
}
