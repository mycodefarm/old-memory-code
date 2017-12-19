package com.jimo.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-5-27.
 */
public class WeatherData implements Subject {
    private List<Observer> observers;
    private float temp;
    private float humidity;
    private float pressure;

    public WeatherData() {
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(temp, humidity, pressure);
        }
    }

    public void mesurementChanged() {
        notifyObservers();
    }

    public void setMesurements(float temp, float humidity, float pressure) {
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        mesurementChanged();
    }
}
