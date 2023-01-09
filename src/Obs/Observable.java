package Obs;

import java.util.ArrayList;

public abstract class Observable {
    private ArrayList<Observer> observers = new ArrayList<>();

    public void addObserver(final Observer observer) {
        this.observers.add(observer);
    }

    public void notifyAllObs(final Object o) {
        for (Observer observer : observers) {
            observer.update(o);
        }
    }
}
