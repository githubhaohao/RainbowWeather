package com.jc.heweather.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jc.heweather.R;
import com.jc.heweather.WeatherApplication;
import com.jc.heweather.model.entity.TrendData;
import com.jc.heweather.weight.LineChart;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HaohaoChang on 2016/12/3.
 */
public class WeatherTrendActivity extends AppCompatActivity {
    @Bind(R.id.tv_page1)
    TextView tvPage1;
    @Bind(R.id.ly_page1)
    RelativeLayout lyPage1;
    @Bind(R.id.tv_page2)
    TextView tvPage2;
    @Bind(R.id.ly_page2)
    RelativeLayout lyPage2;
    @Bind(R.id.line_chart)
    LineChart lineChart;
    private int[] maxTemps = new int[7];
    private int[] minTemps = new int[7];
    private TrendData trendData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        tvPage1.setText("最高温度");
        tvPage2.setText("最低温度");
        trendData = WeatherApplication.getApplication().getTrendData();
        if (trendData != null) {
            lineChart.setScore(trendData.getMaxTemps());
            lineChart.setxAxisTexts(trendData.getxAxisTexts());
        }
    }

    @OnClick({R.id.ly_page1, R.id.ly_page2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ly_page1:
                if (trendData != null) {
                    lineChart.setScore(trendData.getMaxTemps());
                }
                lyPage1.setBackgroundResource(R.drawable.rectangle_left_select);
                tvPage1.setTextColor(Color.parseColor("#ffffff"));
                lyPage2.setBackgroundResource(R.drawable.rectangle_right);
                tvPage2.setTextColor(Color.parseColor("#435356"));
                break;
            case R.id.ly_page2:
                if (trendData != null) {
                    lineChart.setScore(trendData.getMinTemps());
                }
                lyPage1.setBackgroundResource(R.drawable.rectangle_left);
                tvPage1.setTextColor(Color.parseColor("#435356"));
                lyPage2.setBackgroundResource(R.drawable.rectangle_right_select);
                tvPage2.setTextColor(Color.parseColor("#ffffff"));
                break;
        }
    }
}
