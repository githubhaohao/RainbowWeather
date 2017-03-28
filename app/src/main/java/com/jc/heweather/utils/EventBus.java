package com.jc.heweather.utils;

import com.squareup.otto.Bus;

/**
 * Created by HaohaoChang on 2016/8/19.
 */
public class EventBus extends Bus {
    private static Bus bus;
    public static Bus getInstance() {
        if (null == bus) {
            bus = new Bus();
        }
        return bus;
    }
}
