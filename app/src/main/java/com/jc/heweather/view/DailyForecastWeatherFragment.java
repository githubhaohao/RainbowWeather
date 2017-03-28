package com.jc.heweather.view;

import android.support.v4.app.Fragment;;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jc.heweather.R;
import com.jc.heweather.adapter.DailyWeatherAdapter;
import com.jc.heweather.model.entity.DailyForecastWeather;
import com.jc.heweather.utils.EventBus;
import com.jc.heweather.utils.ScrollableHelper;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HaohaoChang on 2016/8/24.
 */
public class DailyForecastWeatherFragment extends Fragment implements ScrollableHelper.ScrollableContainer{

    @Bind(R.id.weather_recycler_view)
    RecyclerView dailyWeatherRecyclerView;
    private DailyWeatherAdapter dailyWeatherAdapter;
    private Bus bus;

    public static DailyForecastWeatherFragment getInstance() {
        DailyForecastWeatherFragment fragment = new DailyForecastWeatherFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.forecast_fragment_layout, container, false);
        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }

    private void initData() {
        bus = EventBus.getInstance();
        bus.register(this);
        dailyWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        dailyWeatherAdapter = new DailyWeatherAdapter(getActivity());
        dailyWeatherRecyclerView.setAdapter(dailyWeatherAdapter);
    }

    @Subscribe
    public void onDailyForecastData(DailyForecastWeather weather) {
        dailyWeatherAdapter.refreshData(weather.getData());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        bus.unregister(this);
    }

    @Override
    public View getScrollableView() {
        return dailyWeatherRecyclerView;
    }
}
