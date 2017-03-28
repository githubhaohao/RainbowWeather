package com.jc.heweather.model;

/**
 * Created by HaohaoChang on 2016/8/19.
 */
public interface IDataSource {
    void getWeatherDataById(String city_id);
    void getWeatherDataByName(String city_name);
    void verifyCity(String cityName);
}
