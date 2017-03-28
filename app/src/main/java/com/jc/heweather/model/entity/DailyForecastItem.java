package com.jc.heweather.model.entity;

import java.io.Serializable;

/**
 * Created by HaohaoChang on 2016/8/18.
 */
public class DailyForecastItem implements Serializable {
    private String date;
    private String hum;   //湿度(%)
    private String pcpn;  //降雨量(mm)
    private String pop;  //降水概率
    private String pres; //气压
    private String vis;  //能见度(km)
    private Tmp tmp;
    private Wind wind;
    private Astro astro;
    private Cond cond;

    public String getDate() {
        return date;
    }

    public String getHum() {
        return hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public String getPop() {
        return pop;
    }

    public String getPres() {
        return pres;
    }

    public String getVis() {
        return vis;
    }

    public Tmp getTmp() {
        return tmp;
    }

    public Wind getWind() {
        return wind;
    }

    public Astro getAstro() {
        return astro;
    }

    public Cond getCond() {
        return cond;
    }

    public class Tmp {  //温度
        String max; //最高温度(摄氏度)
        String min; //最低温度(摄氏度)

        public String getMax() {
            return max;
        }

        public String getMin() {
            return min;
        }
    }

    public class Wind {  //风力状况
        String deg; //风向(角度)
        String dir; //风向(方向)
        String spd; //风速(Kmph)
        String sc;

        public String getSc() {
            return sc;
        }

        public String getDeg() {
            return deg;
        }

        public String getDir() {
            return dir;
        }

        public String getSpd() {
            return spd;
        }
    }


    private class Astro {
        String sr;//日出时间
        String ss;//日落时间

        public String getSr() {
            return sr;
        }

        public String getSs() {
            return ss;
        }
    }

    public class Cond { //天气状况
        String code_d; //白天天气代码
        String code_n; //夜间天气代码
        String txt_d; //白天天气描述
        String txt_n; //夜间天气描述

        public String getCode_d() {
            return code_d;
        }

        public String getCode_n() {
            return code_n;
        }

        public String getTxt_d() {
            return txt_d;
        }

        public String getTxt_n() {
            return txt_n;
        }
    }



}
