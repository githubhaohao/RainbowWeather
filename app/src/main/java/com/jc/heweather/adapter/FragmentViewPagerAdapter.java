package com.jc.heweather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jc.heweather.view.DailyForecastWeatherFragment;

/**
 * Created by HaohaoChang on 2016/8/24.
 */
public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    public FragmentViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new DailyForecastWeatherFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
