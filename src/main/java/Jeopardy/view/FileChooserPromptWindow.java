package Jeopardy.view;

import javax.swing.*;

import Jeopardy.controller.CSVInput;
import Jeopardy.model.Category;
import Jeopardy.observer.LogEvent;
import Jeopardy.observer.LogObserver;
import Jeopardy.observer.LogSubject;
import Jeopardy.observer.Observer;
import Jeopardy.observer.LogEvent.Builder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class FileChooserPromptWindow implements LogSubject{
    private JDialog dialog;
    private List<Category> categories;
    private String filePath;
    private File file;
    private LogObserver eventLogger;

    public FileChooserPromptWindow(Observer o){
        LinkObserver(o);

        LogEvent startupEvent = new LogEvent.Builder()
                .playerName("System")
                .activity("Start Game")
                .build();
        UpdateLogObserver(startupEvent);

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

        dialog.add(new JLabel("Locate your CSV File for this game session"));
        JButton browse = new JButton("Browse Files");
        browse.addActionListener(e ->{
            FileDialog fileDialog = new FileDialog((Frame)null, "", FileDialog.LOAD);
            fileDialog.setFile("*.csv");
            fileDialog.setDirectory(System.getProperty("user.home"));
            fileDialog.setVisible(true);

            String fileDirectory = fileDialog.getDirectory();
            String fileName = fileDialog.getFile();

            file = new File(fileDirectory,fileName);
            browse.setText(fileDirectory);
        });
        dialog.add(browse);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            createCategories(file);
            LogEvent fileLoadEvent = new LogEvent.Builder()
                    .playerName("System")
                    .activity("File Load")
                    .result("Success")
                    .build();
            UpdateLogObserver(fileLoadEvent);
            dialog.dispose();
        });

        dialog.add(submit);

        dialog.setVisible(true);
    }

    public void createCategories(File file){
        CSVInput csvInput = new CSVInput();
        this.categories = csvInput.createCategories(file.getAbsolutePath());
    }

    public List<Category> getCategories(){
        return this.categories;
    }

    @Override
    public void LinkObserver(Observer o) {
        this.eventLogger = (LogObserver) o;
    }

    @Override
    public void UnlinkObserver(Observer o) {

    }

    @Override
    public void LinkLogObserver(Observer o) {

    }

    @Override
    public void UpdateLogObserver(LogEvent logEvent) {
        this.eventLogger.Update(logEvent);
    }

    @Override
    public void UpdateObservers() {

    }
}
