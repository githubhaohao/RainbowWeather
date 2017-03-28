package com.jc.heweather.model.entity;

import java.io.Serializable;

/**
 * Created by HaohaoChang on 2016/8/18.
 */
public class NowWeather implements Serializable {
    private HourlyForecastItem.Wind wind; //风力状况
    private String fl; //体感温度
    private String hum; //湿度(%)
    private String pcpn; //降雨量(mm)
    private String pres; //气压
    private String tmp; //当前温度(摄氏度)
    private String vis; //能见度(km)
    private Cond cond;  //天气状况

    public HourlyForecastItem.Wind getWind() {
        return wind;
    }

    public String getFl() {
        return fl;
    }

    public String getHum() {
        return hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public String getPres() {
        return pres;
    }

    public String getTmp() {
        return tmp;
    }

    public String getVis() {
        return vis;
    }

    public Cond getCond() {
        return cond;
    }

    public class Cond { //天气状况
        String code; //天气代码
        String txt; //	天气描述

        public String getCode() {
            return code;
        }

        public String getTxt() {
            return txt;
        }
    }

}
