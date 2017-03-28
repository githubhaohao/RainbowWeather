package com.jc.heweather.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HaohaoChang on 2016/8/19.
 */
public class Weather implements Serializable {
    private List<WeatherItem> weather;

    public List<WeatherItem> getWeather() {
        return weather;
    }
}
