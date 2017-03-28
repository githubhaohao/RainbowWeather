package com.jc.heweather.presenter;

import android.content.Context;

import com.jc.heweather.model.WeatherDataSource;
import com.jc.heweather.model.entity.Error;
import com.jc.heweather.model.entity.Location;
import com.jc.heweather.model.entity.VerifyMessage;
import com.jc.heweather.model.entity.WeatherItem;
import com.jc.heweather.utils.EventBus;
import com.jc.heweather.view.IMainView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by HaohaoChang on 2016/8/20.
 */
public class WeatherPresenter implements IMainPresenter {

    private IMainView mainView;
    private WeatherDataSource weatherDataSource;
    private Bus bus;

    public WeatherPresenter(Context context,IMainView mainView) {
        this.mainView = mainView;
        this.bus = EventBus.getInstance();
        weatherDataSource = new WeatherDataSource(context,bus);

    }

    @Subscribe
    public void onWeatherData(WeatherItem weatherItem) {

        mainView.hideLoadingView();
        mainView.showWeatherData(weatherItem);

    }

    @Subscribe
    public void onError(Error error) {

        mainView.hideLoadingView();
        mainView.showError(error.getMessage());

    }

    @Subscribe
    public void onVerifyMessage(VerifyMessage message) {
        mainView.hideLoadingView();
        mainView.showVerifyMessage(message);
    }


    @Override
    public void loadWeatherDataById(String city_id) {

        //mainView.showLoadingView();
        weatherDataSource.getWeatherDataById(city_id);

    }

    @Override
    public void loadWeatherDataByName(String city_name) {
        //mainView.showLoadingView();
        weatherDataSource.getWeatherDataByName(city_name);
    }

    @Override
    public void queryCity(String city_name) {
        weatherDataSource.verifyCity(city_name);
    }


    @Override
    public void start() {

        bus.register(this);

    }

    @Override
    public void stop() {

        bus.unregister(this);

    }
}
