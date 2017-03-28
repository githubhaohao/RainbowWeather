package com.jc.heweather.view;

import com.jc.heweather.model.entity.Location;
import com.jc.heweather.model.entity.VerifyMessage;
import com.jc.heweather.model.entity.WeatherItem;

/**
 * Created by HaohaoChang on 2016/8/19.
 */
public interface IMainView {
    void showLoadingView();
    void hideLoadingView();
    void showWeatherData(WeatherItem weatherItem);
    void showError(String error);
    void showVerifyMessage(VerifyMessage message);

}
