package Jeopardy.view;

import javax.swing.*;

import Jeopardy.controller.GameManager;
import Jeopardy.controller.GameManagerLink;
import Jeopardy.model.Category;
import Jeopardy.model.Option;
import Jeopardy.model.Question;
import Jeopardy.observer.ButtonLockLink;
import Jeopardy.observer.EventLogLink;
import Jeopardy.observer.Observer;
import Jeopardy.observer.ScoreComponentLink;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionWindow implements ScoreComponentLink, ButtonLockLink, GameManagerLink, EventLogLink{
    private JDialog dialog;
    private List<OptionButton> options;
    private int score;
    private Category category;

    public QuestionWindow(Question q,Category c){
        //frame to hold dialog
        JFrame frame = new JFrame();
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(300,300);
        frame.setVisible(false);

        dialog = new JDialog(frame, "", true);
        dialog.setSize(500,500);
        dialog.setLayout(new GridLayout(5,1));
        dialog.setLocationRelativeTo(null);

        dialog.add(new JLabel(q.getQuestion()));
        score = q.getScore();
        category = c;

        options = new ArrayList<OptionButton>();

        for(Option o:q.getOptions()){
            OptionButton option = new OptionButton(o,this);
            if(o.getHeader() == q.getCorrectAnswer())
                option.setCorrect(true);
            dialog.add(option.getButton());
            options.add(option);
        }

        //dialog.setVisible(true);
    }

    public void show(){
        dialog.setVisible(true);
    }

    public JDialog getDialog(){
        return this.dialog;
    }

    public int getScore(){
        return this.score;
    }

    public Category getCategory(){
        return this.category;
    }

    @Override
    public void LinkObserver(Observer o) {
        for(OptionButton oB:options)
            oB.LinkObserver(o);
    }

    @Override
    public void LinkLockObserver(Observer o) {
        for(OptionButton oB:options)
            oB.LinkLockObserver(o);
    }

    @Override
    public void LinkGameManager(GameManager gM) {
        for(OptionButton oB:options)
            oB.LinkGameManager(gM);
    }

    @Override
    public void LinkEventLog(Observer o) {
        for(OptionButton oB:options)
            oB.LinkEventLog(o);
    }
}
