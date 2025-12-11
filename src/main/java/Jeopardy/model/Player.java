package Jeopardy.model;

public class Player {
    private String name;
    private int score;
    private final int id; // Unique identifier for the player

    public Player(int id, String name){
        this.name = name;
        this.score = 0;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getId() {
        return id;
    }

    public void addToScore(int additionalScore){
        this.score += additionalScore;
    }
}
