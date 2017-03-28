package com.jc.heweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.heweather.R;
import com.jc.heweather.model.entity.Suggestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HaohaoChang on 2016/8/24.
 */
public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.SuggestionViewHolder> {
    private Context context;
    private List<Map<String, Object>> data;
    private Suggestion suggestion;

    public SuggestionAdapter(Context context, Suggestion suggestion) {
        this.context = context;
        this.suggestion = suggestion;

        data = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("type", "comf");
        map.put("comf", suggestion.getComf());
        data.add(map);
        map = new HashMap<>();
        map.put("type", "cw");
        map.put("cw", suggestion.getCw());
        data.add(map);
        map = new HashMap<>();
        map.put("type", "drsg");
        map.put("drsg", suggestion.getDrsg());
        data.add(map);
        map = new HashMap<>();
        map.put("type", "flu");
        map.put("flu", suggestion.getFlu());
        data.add(map);
        map = new HashMap<>();
        map.put("type", "sport");
        map.put("sport", suggestion.getSport());
        data.add(map);
        map = new HashMap<>();
        map.put("type", "trav");
        map.put("trav", suggestion.getTrav());
        data.add(map);
        map = new HashMap<>();
        map.put("type", "uv");
        map.put("uv", suggestion.getUv());
        data.add(map);

    }

    @Override
    public SuggestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.suggestion_item_layout, parent, false);
        return new SuggestionViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(SuggestionViewHolder holder, int position) {
        Map<String,Object> map = data.get(position);
        String type = (String) map.get("type");
        int resId = 0;
        String format = null;
        Suggestion.Description desc = (Suggestion.Description) map.get(type);
        if (type.contains("comf")) {
            resId = R.drawable.icon_happy_cartoon;
            format = "舒适指数---%s";

        } else if (type.contains("cw")) {
            resId = R.drawable.icon_car_wash;
            format = "洗车指数---%s";

        } else if (type.contains("drsg")) {
            resId = R.drawable.icon_shirt;
            format = "穿衣指数---%s";

        } else if (type.contains("flu")) {
            resId = R.drawable.icon_first_aid;
            format = "感冒指数---%s";

        } else if (type.contains("sport")) {
            resId = R.drawable.icon_sport;
            format = "运动指数---%s";

        } else if (type.contains("trav")) {
            resId = R.drawable.icon_travel;
            format = "旅游指数---%s";

        } else if (type.contains("uv")) {
            resId = R.drawable.icon_umbrella;
            format = "紫外线指数---%s";

        }
        holder.suggestionIcon.setImageResource(resId);
        holder.indexText.setText(String.format(format,desc.getBrf()));
        holder.suggestionText.setText(desc.getTxt());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SuggestionViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.suggestion_icon)
        ImageView suggestionIcon;
        @Bind(R.id.index_text)
        TextView indexText;
        @Bind(R.id.suggestion_text)
        TextView suggestionText;

        public SuggestionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
