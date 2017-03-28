package com.jc.heweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.jc.heweather.WeatherApplication;

/**
 * Created by HaohaoChang on 2016/8/20.
 */
public class SharedPreferenceUtil {
    public static final String CITY_COUNT = "city_count";
    public static final String STORAGE_STATUS = "storage_status";
    public static final String CITY_NAME = "city_name";
    public static final String IS_READY = "is_ready";
    private static SharedPreferenceUtil instance;
    private SharedPreferences preferences;

    public SharedPreferenceUtil(Context context) {
        preferences = context.getSharedPreferences("Setting",Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtil getInstance() {
        if (instance == null) {
            instance = new SharedPreferenceUtil(WeatherApplication.applicationContext);
        }
        return instance;
    }

    public int getInt(String key,int def) {
        return preferences.getInt(key,def);
    }

    public void putInt(String key,int value) {

        preferences.edit().putInt(key,value).apply();
    }

    public String getString(String key,String def) {
        return preferences.getString(key,def);
    }

    public void putString(String key,String value) {
        preferences.edit().putString(key,value).apply();
    }

    public boolean getBoolean(String key,boolean def) {

        return preferences.getBoolean(key,def);

    }

    public void putBoolean(String key,boolean value) {
        preferences.edit().putBoolean(key,value).apply();
    }

    public void commit() {
        preferences.edit().commit();
    }
}
