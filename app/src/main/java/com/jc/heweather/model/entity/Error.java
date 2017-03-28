package com.jc.heweather.model.entity;

/**
 * Created by HaohaoChang on 2016/8/26.
 */
public class Error {
    private String message;
    private String path;

    public Error(String message, String path) {
        this.message = message;
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
