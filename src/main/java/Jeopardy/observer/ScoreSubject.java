package Jeopardy.observer;

public interface ScoreSubject extends Subject{
    void UpdateObserverScore(boolean correct);
}
