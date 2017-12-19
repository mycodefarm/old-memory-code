package com.jimo.observer;

/**
 * Created by root on 17-5-27.
 */
public interface Observer {
    public void update(float temp, float humidity, float pressure);
}
