package com.jc.heweather.model.entity;

import java.io.Serializable;

/**
 * Created by HaohaoChang on 2016/8/18.
 */
public class BasicInfo implements Serializable {
    private String city;
    private String cnty;
    private String id;
    private String lat;
    private String lon;
    private Update update;

    public String getCity() {
        return city;
    }

    public String getCnty() {
        return cnty;
    }

    public String getId() {
        return id;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public Update getUpdate() {
        return update;
    }

    private class Update {
        String loc;
        String utc;

        public String getLoc() {
            return loc;
        }

        public String getUtc() {
            return utc;
        }
    }

}
