package Jeopardy.view;

import javax.swing.*;

import Jeopardy.model.Player;

import java.awt.*;

public class PlayerCell extends JPanel {
    private Player player;
    private JPanel panel;

    public PlayerCell(Player player){
        this.player = player;
        panel = new JPanel(new GridLayout(3,1));
        panel.add(new JLabel(this.player.getName()));
        panel.add(new JLabel("Score : " + this.player.getScore()));

        add(panel);
    }
}
