package com.jimo.observer;

/**
 * Created by root on 17-5-27.
 */
public class Test {
    public static void main(String[] args) {
        WeatherData subject = new WeatherData();

        new CurrentConditionDisplay(subject);

        subject.setMesurements(30.2f, 100, 100000);
        subject.setMesurements(32.2f, 120, 101000);
        subject.setMesurements(33.2f, 110, 102000);
    }
}
