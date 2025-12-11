package Jeopardy.view;

import javax.swing.*;

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

public class AddPlayersPromptWindow implements LogSubject{
    private JDialog dialog;
    private List<Player> players;
    private LogObserver eventLogger;

    public AddPlayersPromptWindow(Observer o){
        LinkObserver(o);
        players = new ArrayList<Player>();
        //frame to hold dialog
        JFrame frame = new JFrame();
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(300,300);
        frame.setVisible(false);

        dialog = new JDialog(frame, "Jeopardy", true);
        dialog.setSize(500,500);
        dialog.setLayout(new GridLayout(3,1));
        dialog.setLocationRelativeTo(null);

        dialog.add(new JLabel("How much players are playing?"));
        Choice playerCount = new Choice();
        playerCount.add("1");
        playerCount.add("2");
        playerCount.add("3");
        playerCount.add("4");
        dialog.add(playerCount);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            int count = playerCount.getSelectedIndex() + 1;
            LogEvent addPlayersEvent = new LogEvent.Builder()
                    .playerName("System")
                    .activity("Select Player Count")
                    .answerGiven(count+"")
                    .build();
            UpdateLogObserver(addPlayersEvent);
            createPlayers(count); //Creates players depending on the number of players selected
        });

        dialog.add(submit);

        dialog.setVisible(true);
    }

    public void createPlayers(int playerCount){
        for(int i = 0; i< playerCount;i++){
            CreatePlayerPromptWindow createPlayerWindow = new CreatePlayerPromptWindow(i, eventLogger);
            createPlayerWindow.LinkObserver(eventLogger);
            Player player = createPlayerWindow.getPlayer();
            players.add(player);
        }
        this.dialog.dispose();
    }

    public List<Player> getPlayers(){
        return this.players;
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
