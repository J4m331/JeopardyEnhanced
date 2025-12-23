package Jeopardy.view;

import javax.swing.*;

import Jeopardy.controller.OllamaInput;
import Jeopardy.model.Category;
import Jeopardy.model.Player;
import Jeopardy.observer.LogEvent;
import Jeopardy.observer.LogObserver;
import Jeopardy.observer.LogSubject;
import Jeopardy.observer.Observer;
import Jeopardy.observer.LogEvent.Builder;

import java.awt.*;

public class CreateCategoryPromptWindow implements LogSubject{
    private Category category;
    private LogObserver eventLogger;

    public CreateCategoryPromptWindow(Observer o){
        LinkObserver(o);

        //frame to hold dialog
        JFrame frame = new JFrame();
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(300,300);
        frame.setVisible(false);

        JDialog dialog = new JDialog(frame, "Jeopardy", true);
        dialog.setSize(300,150);
        dialog.setLayout(new GridLayout(5,1));
        dialog.setLocationRelativeTo(null);

        dialog.add(new JLabel("Enter the name of the category"));
        JTextArea nameField = new JTextArea("Category");
        dialog.add(nameField);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            category = new OllamaInput().createCategory("settings.csv", nameField.getText());
            LogEvent createPlayerEvent = new LogEvent.Builder()
                    .playerName(category.getName())
                    .activity("Create Player")
                    .answerGiven(category.getName())
                    .build();
            UpdateLogObserver(createPlayerEvent);
            dialog.dispose();
        });

        dialog.add(submit);

        dialog.setVisible(true);

    }

    public Category getCategory(){
        return category;
    }

    @Override
    public void LinkLogObserver(Observer o) {

    }

    @Override
    public void UpdateLogObserver(LogEvent logEvent) {
        eventLogger.Update(logEvent);
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
