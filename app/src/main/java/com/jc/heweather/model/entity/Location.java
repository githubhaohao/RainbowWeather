package com.jc.heweather.model.entity;

/**
 * Created by HaohaoChang on 2016/8/26.
 */
public class Location {
    private String location;
    private boolean isOk;

    public Location(String s) {
        if (s.contains("省") && s.contains("市")) {
            location = s.substring(s.indexOf("省") + 1, s.indexOf("市"));
            isOk = true;
        } else if (s.contains("市")) {
            location = s.substring(0, s.indexOf("市"));
            isOk = true;
        } else {
            location = "新蔡";
            isOk = false;
        }
    }

    public String getLocation() {
        return location;
    }

    public boolean isOk() {
        return isOk;
    }
}
