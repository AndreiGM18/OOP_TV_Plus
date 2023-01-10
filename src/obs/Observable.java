package obs;

import java.util.ArrayList;

public abstract class Observable {
    private ArrayList<Observer> observers = new ArrayList<>();

    /**
     * Adds an observer
     * @param observer
     */
    public void addObserver(final Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Notifies all observers
     * @param o an object that is to meant to be used by the observers
     */
    public void notifyAllObs(final Object o) {
        for (Observer observer : observers) {
            observer.update(o);
        }
    }
}
