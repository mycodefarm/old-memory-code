package com.jimo.observer;

/**
 * Created by root on 17-5-27.
 */
public interface Subject {
    public void registerObserver(Observer o);

    public void removeObserver(Observer o);

    public void notifyObservers();
}
