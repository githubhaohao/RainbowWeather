package com.jc.heweather.model.entity;

import java.io.Serializable;

/**
 * Created by HaohaoChang on 2016/8/18.
 */
public class HourlyForecastItem implements Serializable{
    private String date;  //当地日期和时间
    private String hum; //当前温度(摄氏度)
    private String pop; //降水概率
    private String pres; //气压
    private String tmp; //当前温度(摄氏度)
    private Wind wind; //风力状况

    public Wind getWind() {
        return wind;
    }

    public class Wind { //风力状况
        String deg; //风向(角度)
        String dir; //风向(方向)
        String sc;  //风力等级
        String spd; //风速(Kmph)

        public String getDeg() {
            return deg;
        }

        public String getDir() {
            return dir;
        }

        public String getSc() {
            return sc;
        }

        public String getSpd() {
            return spd;
        }
    }

    public String getDate() {
        return date;
    }

    public String getHum() {
        return hum;
    }

    public String getPop() {
        return pop;
    }

    public String getPres() {
        return pres;
    }

    public String getTmp() {
        return tmp;
    }
}
