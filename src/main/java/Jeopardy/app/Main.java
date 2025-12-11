package Jeopardy.app;

import java.util.List;

import Jeopardy.controller.EventLogger;
import Jeopardy.controller.GameManager;
import Jeopardy.model.Category;
import Jeopardy.model.Player;
import Jeopardy.view.AddPlayersPromptWindow;
import Jeopardy.view.FileChooserPromptWindow;
import Jeopardy.view.MainGameFrame;

public class Main {
    public static void main(String[] args){
        EventLogger eventLogger = EventLogger.getEventLoggerInstance();

        FileChooserPromptWindow fileChooser = new FileChooserPromptWindow(eventLogger);
        List<Category> categories = fileChooser.getCategories();

        AddPlayersPromptWindow playersWindow = new AddPlayersPromptWindow(eventLogger);
        List<Player> players = playersWindow.getPlayers();

        GameManager gM = new GameManager();
        gM.addPlayers(players);

        MainGameFrame mainFrame = new MainGameFrame(categories, gM, gM, eventLogger);
        mainFrame.setVisible(true);

    }
}