package Jeopardy.view;

import javax.swing.*;

import Jeopardy.model.Player;
import Jeopardy.observer.LogEvent;
import Jeopardy.observer.LogObserver;
import Jeopardy.observer.LogSubject;
import Jeopardy.observer.Observer;
import Jeopardy.observer.LogEvent.Builder;

import java.awt.*;

public class CreatePlayerPromptWindow implements LogSubject{
    private Player player;
    private LogObserver eventLogger;

    public CreatePlayerPromptWindow(int i, Observer o){
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

        dialog.add(new JLabel("Enter your name"));
        JTextArea nameField = new JTextArea("Player");
        dialog.add(nameField);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            player = new Player(i + 1, nameField.getText());
            LogEvent createPlayerEvent = new LogEvent.Builder()
                    .playerName(player.getName())
                    .activity("Create Player")
                    .answerGiven(player.getName())
                    .build();
            UpdateLogObserver(createPlayerEvent);
            dialog.dispose();
        });

        dialog.add(submit);

        dialog.setVisible(true);

    }

    public Player getPlayer(){
        return player;
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
