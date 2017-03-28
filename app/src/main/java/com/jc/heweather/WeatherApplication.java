package com.jc.heweather;

import android.app.Application;
import android.content.Context;

import com.jc.heweather.model.entity.TrendData;
import com.jc.heweather.utils.SharedPreferenceUtil;

/**
 * Created by HaohaoChang on 2016/8/19.
 */
public class WeatherApplication extends Application {

    public static Context applicationContext = null;
    private static WeatherApplication application;
    private SharedPreferenceUtil sharedPreferenceUtil = null;
    private TrendData trendData = null;

    public TrendData getTrendData() {
        return trendData;
    }

    public void setTrendData(TrendData trendData) {

        this.trendData = trendData;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        application = this;
        initSharedPref();

    }

    public static WeatherApplication getApplication() {
        return application;
    }

    private void initSharedPref() {
        sharedPreferenceUtil = new SharedPreferenceUtil(this);
        if (!sharedPreferenceUtil.getBoolean(SharedPreferenceUtil.STORAGE_STATUS, false)) {
            sharedPreferenceUtil.putInt("100", R.mipmap.a100);
            sharedPreferenceUtil.putInt("101", R.mipmap.a101);
            sharedPreferenceUtil.putInt("102", R.mipmap.a102);
            sharedPreferenceUtil.putInt("103", R.mipmap.a103);
            sharedPreferenceUtil.putInt("104", R.mipmap.a104);
            sharedPreferenceUtil.putInt("200", R.mipmap.b200);
            sharedPreferenceUtil.putInt("201", R.mipmap.b201);
            sharedPreferenceUtil.putInt("202", R.mipmap.b202);
            sharedPreferenceUtil.putInt("203", R.mipmap.b203);
            sharedPreferenceUtil.putInt("204", R.mipmap.b204);
            sharedPreferenceUtil.putInt("205", R.mipmap.b205);
            sharedPreferenceUtil.putInt("206", R.mipmap.b206);
            sharedPreferenceUtil.putInt("207", R.mipmap.b207);
            sharedPreferenceUtil.putInt("208", R.mipmap.b208);
            sharedPreferenceUtil.putInt("209", R.mipmap.b209);
            sharedPreferenceUtil.putInt("300", R.mipmap.c300);
            sharedPreferenceUtil.putInt("300", R.mipmap.c300);
            sharedPreferenceUtil.putInt("301", R.mipmap.c301);
            sharedPreferenceUtil.putInt("302", R.mipmap.c302);
            sharedPreferenceUtil.putInt("303", R.mipmap.c303);
            sharedPreferenceUtil.putInt("304", R.mipmap.c304);
            sharedPreferenceUtil.putInt("305", R.mipmap.c305);
            sharedPreferenceUtil.putInt("306", R.mipmap.c306);
            sharedPreferenceUtil.putInt("307", R.mipmap.c307);
            sharedPreferenceUtil.putInt("308", R.mipmap.c308);
            sharedPreferenceUtil.putInt("309", R.mipmap.c309);
            sharedPreferenceUtil.putInt("310", R.mipmap.c310);
            sharedPreferenceUtil.putInt("311", R.mipmap.c311);
            sharedPreferenceUtil.putInt("312", R.mipmap.c312);
            sharedPreferenceUtil.putInt("313", R.mipmap.c313);
            sharedPreferenceUtil.putInt("400", R.mipmap.d400);
            sharedPreferenceUtil.putInt("401", R.mipmap.d401);
            sharedPreferenceUtil.putInt("402", R.mipmap.d402);
            sharedPreferenceUtil.putInt("403", R.mipmap.d403);
            sharedPreferenceUtil.putInt("404", R.mipmap.d404);
            sharedPreferenceUtil.putInt("405", R.mipmap.d405);
            sharedPreferenceUtil.putInt("406", R.mipmap.d406);
            sharedPreferenceUtil.putInt("407", R.mipmap.d407);
            sharedPreferenceUtil.putInt("500", R.mipmap.e500);
            sharedPreferenceUtil.putInt("501", R.mipmap.e501);
            sharedPreferenceUtil.putInt("502", R.mipmap.e502);
            sharedPreferenceUtil.putInt("503", R.mipmap.e503);
            sharedPreferenceUtil.putInt("504", R.mipmap.e504);
            sharedPreferenceUtil.putInt("505", R.mipmap.e999);
            sharedPreferenceUtil.putInt("506", R.mipmap.e999);
            sharedPreferenceUtil.putInt("507", R.mipmap.e507);
            sharedPreferenceUtil.putInt("508", R.mipmap.e508);
            sharedPreferenceUtil.putInt("900", R.mipmap.e900);
            sharedPreferenceUtil.putInt("901", R.mipmap.e901);
            sharedPreferenceUtil.putInt("999", R.mipmap.e999);
            sharedPreferenceUtil.putBoolean(SharedPreferenceUtil.STORAGE_STATUS, true);
        }
    }

}
