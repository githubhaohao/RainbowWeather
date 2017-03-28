package com.jc.heweather.presenter;

/**
 * Created by HaohaoChang on 2016/8/19.
 */
public interface IMainPresenter {
    void loadWeatherDataById(String city_id);
    void loadWeatherDataByName(String city_name);
    void queryCity(String city_name);
    void start();
    void stop();

}
