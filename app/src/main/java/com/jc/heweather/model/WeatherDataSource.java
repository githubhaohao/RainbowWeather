package com.jc.heweather.model;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jc.heweather.model.entity.Error;
import com.jc.heweather.model.entity.VerifyMessage;
import com.jc.heweather.model.entity.WeatherItem;
import com.jc.heweather.utils.ConstantData;
import com.orhanobut.logger.Logger;
import com.squareup.otto.Bus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


/**
 * Created by HaohaoChang on 2016/8/19.
 */
public class WeatherDataSource implements IDataSource {
    private Bus bus;
    private Gson gson;
    private RequestQueue requestQueue;

    public WeatherDataSource(Context context ,Bus bus) {
        Logger.init("[WeatherDataSource]");
        gson = new Gson();
        requestQueue = Volley.newRequestQueue(context);
        this.bus = bus;
    }

    @Override
    public void getWeatherDataById(String city_id) {
        StringBuilder sb = new StringBuilder(ConstantData.BASE_URL).append(city_id);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(sb.toString(),null,new JSONResponseListener(),new ErrorResponseListener());
        requestQueue.add(jsonObjectRequest);
        requestQueue.start();
    }

    @Override
    public void getWeatherDataByName(String city_name) {
        StringBuilder sb = new StringBuilder(ConstantData.CITY_NAME_URL).append(city_name);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(sb.toString(),null,new JSONResponseListener(),new ErrorResponseListener());
        requestQueue.add(jsonObjectRequest);
        requestQueue.start();
    }

    @Override
    public void verifyCity(String cityName) {
        StringBuilder sb = new StringBuilder(ConstantData.CITY_NAME_URL).append(cityName);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(sb.toString(),null,new VerifyJSONResponseListener(),new ErrorResponseListener());
        requestQueue.add(jsonObjectRequest);
        requestQueue.start();

    }

    private class JSONResponseListener implements Response.Listener<JSONObject> {
        @Override
        public void onResponse(JSONObject response) {
            Logger.json(response.toString());
            postData(response);
        }
    }

    private class VerifyJSONResponseListener implements Response.Listener<JSONObject> {
        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONArray weatherArray = response.getJSONArray(ConstantData.JSON_KEY);
                JSONObject weatherItemJson = weatherArray.getJSONObject(0);
                if (weatherArray.length() == 1) {
                    if (weatherItemJson.getString("status").equals("ok")) {
                        bus.post(new VerifyMessage(true, null));
                    } else {
                        bus.post(new VerifyMessage(false, "啊哦！该城市不存在。"));
                    }

                } else if (weatherArray.length() > 1) {
                    bus.post(new VerifyMessage(false,"查询到多个同名城市，请选择更高级别的行政区"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class ErrorResponseListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            Logger.e("[VolleyError]",error.getMessage());
            bus.post(new Error(error.getMessage(),null));
        }
    }

    private void postData(JSONObject jsonObject) {
        try {
            JSONArray weatherArray = jsonObject.getJSONArray(ConstantData.JSON_KEY);
            JSONObject weatherItemJson = weatherArray.getJSONObject(0);
            WeatherItem weatherItem = gson.fromJson(weatherItemJson.toString(),WeatherItem.class);
            Logger.i(weatherItem.getStatus());
            bus.post(weatherItem);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
