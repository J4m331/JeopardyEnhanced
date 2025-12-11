package Jeopardy.view;

import javax.swing.*;

import Jeopardy.controller.GameManager;
import Jeopardy.controller.GameManagerLink;
import Jeopardy.model.Category;
import Jeopardy.model.Question;
import Jeopardy.observer.ButtonLockObserver;
import Jeopardy.observer.EventLogLink;
import Jeopardy.observer.Observer;
import Jeopardy.observer.ScoreComponentLink;

import java.awt.*;

public class QuestionButton implements ScoreComponentLink, ButtonLockObserver, GameManagerLink, EventLogLink{

    private JButton qBtn;
    private QuestionWindow qW;
    private boolean locked;

    public QuestionButton(Question q, Category c){
        qBtn = new JButton(q.getScore()+"");

        Font buttonFont = new Font("Impact",Font.BOLD,50);
        qBtn.setFont(buttonFont);
        qBtn.setForeground(Color.ORANGE);
        qBtn.setBackground(new Color(0,0,160));

        this.qW = new QuestionWindow(q,c);
        this.locked = false;
        LinkLockObserver(this);
        qBtn.addActionListener(e ->{
            this.qW.show();
        });
    }

    public JButton getButton(){
        return qBtn;
    }

    @Override
    public void LinkObserver(Observer o) {
        this.qW.LinkObserver(o);
    }

    public void LinkLockObserver(Observer o){
        this.qW.LinkLockObserver(o);
    }

    @Override
    public void Update() {
        this.qBtn.setEnabled(false);
        this.locked = true;
        this.qBtn.setBackground(new Color(0,0,80));
    }

    @Override
    public void LinkGameManager(GameManager gM) {
        this.qW.LinkGameManager(gM);
    }

    @Override
    public void LinkEventLog(Observer o) {
        this.qW.LinkEventLog(o);
    }
}
