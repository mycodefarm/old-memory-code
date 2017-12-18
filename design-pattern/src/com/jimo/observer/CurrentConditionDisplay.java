package com.jimo.observer;

/**
 * Created by root on 17-5-27.
 */
public class CurrentConditionDisplay implements Observer, Display {
    private float temp;
    private float humidity;
    private Subject weatherData;

    public CurrentConditionDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.println("现在的气温和湿度：" + temp + "," + humidity);
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temp = temp;
        this.humidity = humidity;
        display();
    }
}
