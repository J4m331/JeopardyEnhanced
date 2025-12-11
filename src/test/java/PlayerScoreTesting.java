import org.junit.jupiter.api.Test;

import Jeopardy.model.Player;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerScoreTesting{
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player(1, "TestPlayer");
    }//Initial setup with a Player named TestPlayer with an ID of 1.

    @Test
    public void addScoreTest(){
        player.addToScore(100);
        assertEquals(100, player.getScore());
    }//Test to ensure that adding to a player's score works correctly.

    @Test
    public void deductScoreTest(){
        player.addToScore(-50);
        assertEquals(-50, player.getScore());
    }//Test to ensure that deducting from a player's score works correctly.

    @Test
    public void multiScoreTest(){
        player.addToScore(200);
        player.addToScore(300);
        player.addToScore(-75);
        assertEquals(425, player.getScore());
    }//Test to ensure that multiple score modifications work correctly.
}