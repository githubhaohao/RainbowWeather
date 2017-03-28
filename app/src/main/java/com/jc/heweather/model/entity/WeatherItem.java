package com.jc.heweather.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HaohaoChang on 2016/8/18.
 */
public class WeatherItem implements Serializable {
    private BasicInfo basic;
    private List<DailyForecastItem> daily_forecast;
    private List<HourlyForecastItem> hourly_forecast;
    private NowWeather now;
    private String status;
    private Suggestion suggestion;

    public BasicInfo getBasic() {
        return basic;
    }

    public List<DailyForecastItem> getDaily_forecast() {
        return daily_forecast;
    }

    public List<HourlyForecastItem> getHourly_forecast() {
        return hourly_forecast;
    }

    public NowWeather getNow() {
        return now;
    }

    public String getStatus() {
        return status;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }
}
