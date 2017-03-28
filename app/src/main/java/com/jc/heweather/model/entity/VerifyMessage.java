package com.jc.heweather.model.entity;

/**
 * Created by HaohaoChang on 2016/8/27.
 */
public class VerifyMessage {
    private boolean isOk;
    private String message;

    public VerifyMessage(boolean isOk, String message) {
        this.isOk = isOk;
        this.message = message;
    }

    public boolean isOk() {
        return isOk;
    }

    public String getMessage() {
        return message;
    }
}
