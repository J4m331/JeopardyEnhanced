package Jeopardy.model;

import java.util.List;

import Jeopardy.model.Option;

public class Question {
    private int score;
    private String question;
    private List<Option> options;
    private char correctAnswer;
    private boolean answered;

    public Question(int score, String question, List<Option> options, char correctAnswer){
        this.score = score;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.answered = false;
    }

    public int getScore() {
        return score;
    }

    public String getQuestion(){
        return question;
    }

    public List<Option> getOptions() {
        return options;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void markAnswered() {
        this.answered = true;
    }

}
