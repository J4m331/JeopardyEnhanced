package Jeopardy.view;

import Jeopardy.observer.LogEvent;
import Jeopardy.observer.LogObserver;
import Jeopardy.observer.LogSubject;
import Jeopardy.observer.Observer;

import javax.swing.*;
import java.awt.*;

public class AddModelPromptWindow implements LogSubject {
    private JDialog dialog;
    private LogObserver eventLogger;
    private String OllamaModel;

    public AddModelPromptWindow(Observer o){
        LinkObserver(o);
        //frame to hold dialog
        JFrame frame = new JFrame();
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(300,300);
        frame.setVisible(false);

        dialog = new JDialog(frame, "Jeopardy", true);
        dialog.setSize(500,500);
        dialog.setLayout(new GridLayout(3,1));
        dialog.setLocationRelativeTo(null);

        dialog.add(new JLabel("New to Jeopardy?, Add your AI model"));

        JTextArea modelInput = new JTextArea();

        dialog.add(modelInput);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e ->{
            OllamaModel = modelInput.getText();
            LogEvent addPlayersEvent = new LogEvent.Builder()
                    .playerName("System")
                    .activity("Select AI Model")
                    .answerGiven(OllamaModel)
                    .build();
            UpdateLogObserver(addPlayersEvent);

            this.dialog.dispose();
        });

        dialog.add(submit);

        dialog.setVisible(true);
    }

    public String getModelString(){
        return OllamaModel;
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
