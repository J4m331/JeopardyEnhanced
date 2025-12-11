package Jeopardy.observer;

import Jeopardy.model.Player;

public interface ScoreUIObserver extends Observer{
    void UpdateUI(Player currentPlayer);
}
