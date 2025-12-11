package Jeopardy.view;

import javax.swing.*;

import Jeopardy.controller.GameManager;
import Jeopardy.controller.GameManagerLink;
import Jeopardy.model.Category;
import Jeopardy.model.Question;
import Jeopardy.observer.EventLogLink;
import Jeopardy.observer.Observer;
import Jeopardy.observer.ScoreComponentLink;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class CategoryPanel extends JPanel implements ScoreComponentLink, GameManagerLink, EventLogLink{

    private JPanel panel = new JPanel(new GridLayout(6,1));
    private List<QuestionButton> questionButtons;

    public CategoryPanel(Category c){
        setLayout(new BorderLayout()); //Forces panel to take up entire space

        JLabel categoryTitle = new JLabel(c.getName());

        Font categoryFont = new Font("Impact",0,30);
        categoryTitle.setFont(categoryFont);
        categoryTitle.setHorizontalAlignment(SwingConstants.CENTER);
        categoryTitle.setOpaque(true);
        categoryTitle.setForeground(Color.ORANGE);
        categoryTitle.setBackground(new Color(0,0,120));

        questionButtons = new ArrayList<QuestionButton>();
        panel.add(categoryTitle);

        for(Question q:c.getQuestions()){
            QuestionButton questionBtn = new QuestionButton(q,c);
            panel.add(questionBtn.getButton());
            questionButtons.add(questionBtn);
        }
        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void LinkObserver(Observer o) {
        for(QuestionButton qBtn:this.questionButtons)
            qBtn.LinkObserver(o);
    }

    @Override
    public void LinkGameManager(GameManager gM) {
        for(QuestionButton qBtn:this.questionButtons)
            qBtn.LinkGameManager(gM);
    }

    @Override
    public void LinkEventLog(Observer o) {
        for(QuestionButton qBtn:this.questionButtons)
            qBtn.LinkEventLog(o);
    }
}