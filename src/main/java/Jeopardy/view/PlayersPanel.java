package Jeopardy.view;

import javax.swing.*;

import Jeopardy.model.Player;
import Jeopardy.observer.ScoreUIObserver;

import java.awt.*;
import java.util.List;

public class PlayersPanel extends JPanel implements ScoreUIObserver{
    private List<Player> players;
    private JPanel panel;

    public PlayersPanel(List<Player> players){
        this.players = players;
        panel = new JPanel(new GridLayout(1,4));
        UpdateUI(players.getFirst()); //Called once to render cells on initialization
        add(panel);
    }

    @Override
    public void Update(){

    }

    @Override
    public void UpdateUI(Player currentPlayer) {
        panel.removeAll();
        for(Player p:this.players){
            PlayerCell pC = new PlayerCell(p);
            if(currentPlayer.equals(p)){
                pC.setBackground(Color.red);
                pC.setOpaque(true);
            }
            else{
                pC.setBackground(Color.WHITE);
                pC.setOpaque(true);
            }
            panel.add(pC);
        }
        panel.revalidate();
        panel.repaint();
    }
}
