package com.jc.heweather.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HaohaoChang on 2016/8/25.
 */
public class DailyForecastWeather implements Serializable{

    private List<DailyForecastItem> data = null;

    public DailyForecastWeather(List<DailyForecastItem> data) {
        this.data = data;
    }

    public List<DailyForecastItem> getData() {
        return data;
    }
}
