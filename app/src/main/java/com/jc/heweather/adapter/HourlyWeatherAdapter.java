package com.jc.heweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jc.heweather.R;
import com.jc.heweather.model.entity.HourlyForecastItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HaohaoChang on 2016/8/23.
 */
public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherViewHolder> {

    private List<HourlyForecastItem> data;
    private Context context;

    public HourlyWeatherAdapter(List<HourlyForecastItem> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public HourlyWeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_hourly_forecast, parent, false);
        return new HourlyWeatherViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(HourlyWeatherViewHolder holder, int position) {
        HourlyForecastItem hourlyForecastItem = data.get(position);
        String timeString = hourlyForecastItem.getDate();
        holder.clockText.setText(timeString.substring(timeString.indexOf(" "),timeString.length()));
        holder.tempText.setText(String.format("%s℃",hourlyForecastItem.getTmp()));
        holder.popText.setText(String.format("%s%%",hourlyForecastItem.getPop()));
        String windSc = hourlyForecastItem.getWind().getSc();
        if (hourlyForecastItem.getWind().getSc().equals("-")) {
            windSc += "级";
        }
        holder.windText.setText(windSc);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class HourlyWeatherViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.clock_text)
        TextView clockText;
        @Bind(R.id.temp_text)
        TextView tempText;
        @Bind(R.id.pop_text)
        TextView popText;
        @Bind(R.id.wind_text)
        TextView windText;

        public HourlyWeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
