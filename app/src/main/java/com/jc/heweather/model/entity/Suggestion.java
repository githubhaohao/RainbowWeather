package com.jc.heweather.model.entity;

import java.io.Serializable;

/**
 * Created by HaohaoChang on 2016/8/18.
 */
public class Suggestion implements Serializable {
    private Description comf; //感觉
    private Description cw; //洗车指数
    private Description drsg; //穿衣指数
    private Description flu; //感冒指数
    private Description sport; //运动指数
    private Description trav; //旅游指数
    private Description uv; //紫外线指数

    public Description getComf() {
        return comf;
    }

    public Description getCw() {
        return cw;
    }

    public Description getDrsg() {
        return drsg;
    }

    public Description getFlu() {
        return flu;
    }

    public Description getSport() {
        return sport;
    }

    public Description getTrav() {
        return trav;
    }

    public Description getUv() {
        return uv;
    }

    public class Description {
        String brf;
        String txt;

        public String getBrf() {
            return brf;
        }

        public String getTxt() {
            return txt;
        }
    }
}
