package Jeopardy.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.*;

import Jeopardy.controller.EventLogger;
import Jeopardy.controller.GameManager;
import Jeopardy.controller.GameManagerLink;
import Jeopardy.controller.ReportGenerator;
import Jeopardy.model.Category;
import Jeopardy.observer.EventLogLink;
import Jeopardy.observer.Observer;
import Jeopardy.observer.ScoreComponentLink;

public class MainGameFrame extends JFrame implements ScoreComponentLink , GameManagerLink, EventLogLink{

    private JPanel mainPanel;
    private JPanel mainGamePanel;
    private PlayersPanel playersPanel;
    private List<CategoryPanel> categoryPanels;

    public MainGameFrame(List<Category> categories, GameManager gM, Observer gameManager, EventLogger eventLogger){
        mainPanel = new JPanel(new BorderLayout());
        mainGamePanel = new JPanel(new GridLayout(1,5,0,0));
        playersPanel = new PlayersPanel(gM.getPlayers());
        setTitle("Jeopardy");
        setSize(1920,1080);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ReportGenerator.generateTextReport("jeopardyGameReport.txt", gM.getPlayers(), "game_event_log.csv");
                dispose();
                System.exit(0);
            }
        });


        categoryPanels = new ArrayList<CategoryPanel>();

        for(Category c:categories){
            CategoryPanel cPanel = new CategoryPanel(c);
            mainGamePanel.add(cPanel);
            categoryPanels.add(cPanel);
        }

        mainPanel.add(mainGamePanel,BorderLayout.CENTER);
        mainPanel.add(playersPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        LinkObserver(gameManager);
        LinkGameManager(gM);
        LinkEventLog(eventLogger);
        gM.LinkObserver(playersPanel);
    }


    @Override
    public void LinkObserver(Observer o) {
        for(CategoryPanel cPanel:this.categoryPanels) {
            cPanel.LinkObserver(o);
        }
    }

    @Override
    public void LinkGameManager(GameManager gM) {
        for(CategoryPanel cPanel:this.categoryPanels){
            cPanel.LinkGameManager(gM);
        }
    }

    @Override
    public void LinkEventLog(Observer o) {
        for(CategoryPanel cPanel:this.categoryPanels){
            cPanel.LinkEventLog(o);
        }
    }
}