package Jeopardy.observer;

public abstract interface Subject {
    void LinkObserver(Observer o);
    void UnlinkObserver(Observer o);
    void UpdateObservers();
}
