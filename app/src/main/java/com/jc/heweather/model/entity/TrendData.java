package com.jc.heweather.model.entity;

import java.io.Serializable;

/**
 * Created by HaohaoChang on 2016/12/3.
 */
public class TrendData implements Serializable {
    private int[] maxTemps;
    private int[] minTemps;
    private String[] xAxisTexts;

    public TrendData(int[] maxTemps, int[] minTemps, String[] xAxisTexts) {
        this.maxTemps = maxTemps;
        this.minTemps = minTemps;
        this.xAxisTexts = xAxisTexts;
    }

    public int[] getMaxTemps() {
        return maxTemps;
    }

    public int[] getMinTemps() {
        return minTemps;
    }

    public String[] getxAxisTexts() {
        return xAxisTexts;
    }
}
