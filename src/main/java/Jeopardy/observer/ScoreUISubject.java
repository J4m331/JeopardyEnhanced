package Jeopardy.observer;

import Jeopardy.model.Player;

public interface ScoreUISubject extends Subject{
    void UpdateUI(Player currentPlayer);
}
