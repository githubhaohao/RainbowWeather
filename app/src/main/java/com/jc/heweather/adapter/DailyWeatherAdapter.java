package com.jc.heweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jc.heweather.R;
import com.jc.heweather.WeatherApplication;
import com.jc.heweather.model.entity.DailyForecastItem;
import com.jc.heweather.model.entity.TrendData;
import com.jc.heweather.utils.SharedPreferenceUtil;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HaohaoChang on 2016/8/25.
 */
public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.DailyWeatherViewHolder> {

    private Context context;
    private List<DailyForecastItem> data = new ArrayList<>();
    private SharedPreferenceUtil sharedPreferenceUtil;

    public DailyWeatherAdapter(Context context) {
        this.context = context;
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance();
    }

    public DailyWeatherAdapter(Context context, List<DailyForecastItem> data) {
        this.context = context;
        this.data = data;
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance();
    }

    @Override
    public DailyWeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_daily_forecast, parent, false);
        return new DailyWeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyWeatherViewHolder holder, int position) {
        DailyForecastItem item = data.get(position);
        String weekDay = null;
        holder.setBackground(item.getCond().getCode_d());
        holder.dateText.setText(getDate(item.getDate()));
        holder.codeDIcon.setImageResource(sharedPreferenceUtil.getInt(item.getCond().getCode_d(), R.mipmap.e999));
        holder.txtDText.setText(item.getCond().getTxt_d());
        holder.maxMinTemText.setText(String.format("%s℃/%s℃", item.getTmp().getMax(), item.getTmp().getMin()));
        if (position == 0) {
            weekDay = "今天";
        } else if (position == 1) {
            weekDay = "明天";
        } else {
            weekDay = getWeek(item.getDate());
        }
        if (weekDay != null) {
            holder.weekDayText.setText(weekDay);
        }
        String windSc = item.getWind().getSc();
        if (item.getWind().getSc().equals("-")) {
            windSc += "级";
        }
        holder.forecastText.setText(String.format("%s 降雨量:%smm 降水概率:%s%%", windSc, item.getPcpn(), item.getPop()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class DailyWeatherViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.code_d_icon)
        ImageView codeDIcon;
        @Bind(R.id.txt_d_text)
        TextView txtDText;
        @Bind(R.id.week_day_text)
        TextView weekDayText;
        @Bind(R.id.max_min_tem_text)
        TextView maxMinTemText;
        @Bind(R.id.forecast_text)
        TextView forecastText;
        @Bind(R.id.root_view)
        LinearLayout rootView;
        @Bind(R.id.date_text)
        TextView dateText;

        public DailyWeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setBackground(String code_d) {
            if (code_d.contains("100")) {
                rootView.setBackgroundColor(context.getResources().getColor(R.color.sunny_sunshine));
            }

        }
    }

    public void refreshData(List<DailyForecastItem> list) {
        if (data.size() > 0) {
            data.clear();
        }
        data = list;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int[] maxTemps = new int[data.size()];
                int[] minTemps = new int[data.size()];
                String[] xAxisTexts = new String[data.size()];
                for (int i = 0; i < data.size(); i++) {
                    DailyForecastItem item = data.get(i);
                    maxTemps[i] = Integer.parseInt(item.getTmp().getMax());
                    minTemps[i] = Integer.parseInt(item.getTmp().getMin());
                    if (i == 0) {
                        xAxisTexts[i] = "今天";
                    } else if (i == 1) {
                        xAxisTexts[i] = "明天";
                    } else {
                        xAxisTexts[i] = getWeek(item.getDate());
                    }
                }
                WeatherApplication.getApplication().setTrendData(new TrendData(maxTemps,minTemps,xAxisTexts));
            }
        }).start();
        notifyDataSetChanged();
    }


    //  String pTime = "2012-03-12";
    private String getWeek(String pTime) {

        String Week = "周";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }
        return Week;
    }

    /*
    * 2015-08-25
    * */
    private String getDate(String s) {
        String month = null;
        String day = null;
        String s1 = s.substring(s.indexOf("-") + 1, s.indexOf("-") + 2);
        if (s1.equals("0")) {
            month = s.substring(s.indexOf("-") + 2, s.indexOf("-") + 3);
        } else {
            month = s.substring(s.indexOf("-") + 1, s.indexOf("-") + 3);
        }

        String s2 = s.substring(s.indexOf("-") + 4, s.indexOf("-") + 5);

        if (s2.equals("0")) {
            day = s.substring(s.indexOf("-") + 5, s.length());
        } else {
            day = s.substring(s.indexOf("-") + 4, s.length());
        }
        return String.format("%s月%s日", month, day);
    }

}

