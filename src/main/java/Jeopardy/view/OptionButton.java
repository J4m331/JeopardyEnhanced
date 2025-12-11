package Jeopardy.view;

import javax.swing.*;

import Jeopardy.controller.GameManager;
import Jeopardy.controller.GameManagerLink;
import Jeopardy.model.Option;
import Jeopardy.observer.ButtonLockObserver;
import Jeopardy.observer.ButtonLockSubject;
import Jeopardy.observer.EventLogLink;
import Jeopardy.observer.LogEvent;
import Jeopardy.observer.LogObserver;
import Jeopardy.observer.LogSubject;
import Jeopardy.observer.Observer;
import Jeopardy.observer.ScoreObserver;
import Jeopardy.observer.ScoreSubject;
import Jeopardy.observer.LogEvent.Builder;

public class OptionButton implements ScoreSubject, ButtonLockSubject, LogSubject , GameManagerLink, EventLogLink{

    private JButton oBtn;
    private boolean correct;
    private ScoreObserver gM;
    private GameManager gameManager;
    private LogObserver logObserver;
    private ButtonLockObserver button;
    private int score;
    private String result;

    public OptionButton(Option o, QuestionWindow qW){
        oBtn = new JButton(o.getContent());
        score = qW.getScore();
        oBtn.addActionListener(e ->{
            if(this.correct == true)
                result = "CORRECT";
            else
                result = "INCORRECT";
            UpdateObserverScore(this.correct);
            UpdateLockObserver(button);
            LogEvent playEvent = new LogEvent.Builder()
                    .playerName(gameManager.getCurrentPlayer().getName())
                    .activity("Answering Question")
                    .category(qW.getCategory().getName())
                    .questionValue(score)
                    .answerGiven(o.getContent()+"")
                    .result(result)
                    .scoreAfterPlay(gameManager.getCurrentPlayer().getScore()+"")
                    .build();
            UpdateLogObserver(playEvent);
            qW.getDialog().dispose();
        });
    }

    public JButton getButton(){
        return oBtn;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @Override
    public void LinkObserver(Observer o) {
        this.gM = (ScoreObserver) o;
    }

    public void LinkLockObserver(Observer o){
        this.button = (ButtonLockObserver) o;
    }

    @Override
    public void UnlinkObserver(Observer o) {

    }

    @Override
    public void UpdateObservers() {

    }

    @Override
    public void UpdateObserverScore(boolean correct) {
        if(correct)
            this.gM.UpdateScore(score);
        else
            this.gM.UpdateScore(score * -1);
    }

    @Override
    public void UpdateLockObserver(Observer o) {
        o.Update();
    }

    @Override
    public void LinkLogObserver(Observer o) {
        this.logObserver = (LogObserver) o;
    }

    @Override
    public void UpdateLogObserver(LogEvent logEvent) {
        this.logObserver.Update(logEvent);
    }

    @Override
    public void LinkGameManager(GameManager gM) {
        this.gameManager = gM;
    }

    @Override
    public void LinkEventLog(Observer o) {
        this.logObserver = (LogObserver) o;
    }
}
