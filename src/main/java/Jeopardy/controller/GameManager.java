package Jeopardy.controller;

import java.util.List;

import Jeopardy.model.Player;
import Jeopardy.observer.Observer;
import Jeopardy.observer.ScoreObserver;
import Jeopardy.observer.ScoreUIObserver;
import Jeopardy.observer.ScoreUISubject;


public class GameManager implements ScoreObserver , ScoreUISubject{
    private static GameManager gmInstance;
    private List<Player> players;
    private Player currentPlayer;
    private int playerIndex;
    private int playerCount;
    private int questionCount;
    private ScoreUIObserver playerPanelUI;

    public GameManager(){
        this.players = null;
        this.questionCount = 0;
    }
/*
    public static GameManager getGmInstance(){
        if(gmInstance == null)
            gmInstance = new GameManager();
        return gmInstance;
    }*/

    public void addPlayers(List<Player> players){
        this.players = players;
        currentPlayer = this.players.getFirst();
        playerIndex = 0;
        playerCount = players.size();
    }

    public List<Player> getPlayers(){
        return this.players;
    }

    @Override
    public void UpdateScore(int score){
        // Apply score to current player
        currentPlayer.addToScore(score);

        // Every answered question counts toward the question total
        questionCount++;
        UpdateUI(currentPlayer);
        System.out.println(questionCount);

        // End game check
        if(questionCount > 24){
            ReportGenerator.generateTextReport("jeopardyGameReport.txt", players, "game_event_log.csv");
            System.exit(0);
        }

        // If the player lost points (incorrect answer), shift to next player.
        // If they gained points, they keep control and do not shift.
        if(score < 0) {
            shiftPlayer();
        }
    }

    public void shiftPlayer(){
        playerIndex = playerIndex + 1;
        if(playerIndex >= playerCount)
            playerIndex = 0;
        currentPlayer = players.get(playerIndex);
        UpdateUI(currentPlayer);
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    @Override
    public void Update(){
        this.UpdateObservers();
    }

    @Override
    public void LinkObserver(Observer o) {
        this.playerPanelUI = (ScoreUIObserver) o;
    }

    @Override
    public void UnlinkObserver(Observer o) {

    }

    @Override
    public void UpdateObservers() {

    }

    @Override
    public void UpdateUI(Player currentPlayer) {
        this.playerPanelUI.UpdateUI(currentPlayer);
    }
}
