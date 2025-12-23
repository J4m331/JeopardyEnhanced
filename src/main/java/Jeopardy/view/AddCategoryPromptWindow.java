package Jeopardy.view;

import javax.swing.*;

import Jeopardy.model.Category;
import Jeopardy.model.Player;
import Jeopardy.observer.LogEvent;
import Jeopardy.observer.LogObserver;
import Jeopardy.observer.LogSubject;
import Jeopardy.observer.Observer;
import Jeopardy.observer.LogEvent.Builder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddCategoryPromptWindow implements LogSubject{
    private JDialog dialog;
    private List<Category> categories;
    private LogObserver eventLogger;

    public AddCategoryPromptWindow(Observer o){
        LinkObserver(o);
        categories = new ArrayList<Category>();
        //frame to hold dialog
        JFrame frame = new JFrame();
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(300,300);
        frame.setVisible(false);

        dialog = new JDialog(frame, "Jeopardy", true);
        dialog.setSize(500,500);
        dialog.setLayout(new GridLayout(3,1));
        dialog.setLocationRelativeTo(null);

        /*
        dialog.add(new JLabel("How much players are playing?"));
        Choice playerCount = new Choice();
        playerCount.add("1");
        playerCount.add("2");
        playerCount.add("3");
        playerCount.add("4");
        playerCount.add("5");
        playerCount.add("6");
        playerCount.add("7");
        playerCount.add("8");
        playerCount.add("9");
        playerCount.add("10");
        dialog.add(playerCount);*/

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            int count = 5;
            LogEvent addPlayersEvent = new LogEvent.Builder()
                    .playerName("System")
                    .activity("Select Player Count")
                    .answerGiven(count+"")
                    .build();
            UpdateLogObserver(addPlayersEvent);
            createCategories(count);
        });

        dialog.add(submit);

        dialog.setVisible(true);
    }

    public void createCategories(int categoryCount){
        for(int i = 0; i< categoryCount;i++){
            CreateCategoryPromptWindow createCategoryWindow = new CreateCategoryPromptWindow(eventLogger);
            createCategoryWindow.LinkObserver(eventLogger);
            Category category = createCategoryWindow.getCategory();
            categories.add(category);
        }
        this.dialog.dispose();
    }

    public List<Category> getCategories(){
        return this.categories;
    }

    @Override
    public void LinkLogObserver(Observer o) {

    }

    @Override
    public void UpdateLogObserver(LogEvent logEvent) {
        this.eventLogger.Update(logEvent);
    }

    @Override
    public void LinkObserver(Observer o) {
        this.eventLogger = (LogObserver) o;
    }

    @Override
    public void UnlinkObserver(Observer o) {

    }

    @Override
    public void UpdateObservers() {

    }
}
