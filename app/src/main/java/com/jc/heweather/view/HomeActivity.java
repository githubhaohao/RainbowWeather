package com.jc.heweather.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.github.rubensousa.floatingtoolbar.FloatingToolbar;
import com.jc.heweather.R;
import com.jc.heweather.adapter.HourlyWeatherAdapter;
import com.jc.heweather.model.entity.BasicInfo;
import com.jc.heweather.model.entity.DailyForecastWeather;
import com.jc.heweather.model.entity.NowWeather;
import com.jc.heweather.model.entity.VerifyMessage;
import com.jc.heweather.model.entity.WeatherItem;
import com.jc.heweather.network.NetStatusReceiver;
import com.jc.heweather.network.NetworkUtil;
import com.jc.heweather.presenter.WeatherPresenter;
import com.jc.heweather.utils.ConstantData;
import com.jc.heweather.utils.EventBus;
import com.jc.heweather.utils.ScrollableHelper;
import com.jc.heweather.utils.SharedPreferenceUtil;
import com.orhanobut.logger.Logger;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import br.com.mauker.materialsearchview.MaterialSearchView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by HaohaoChang on 2016/8/24.
 */
public class HomeActivity extends AppCompatActivity implements IMainView, PtrHandler, ViewPager.OnPageChangeListener, FloatingToolbar.ItemClickListener {

    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.code_icon)
    ImageView codeIcon;
    @Bind(R.id.now_weather_txt)
    TextView nowWeatherTxt;
    @Bind(R.id.city_text)
    TextView cityText;
    @Bind(R.id.temp_text)
    TextView tempText;
    @Bind(R.id.fl_hum_text)
    TextView flHumText;
    @Bind(R.id.header_layout)
    LinearLayout headerLayout;
    @Bind(R.id.tv_page1)
    TextView tvPage1;
    @Bind(R.id.ly_page1)
    RelativeLayout lyPage1;
    @Bind(R.id.tv_page2)
    TextView tvPage2;
    @Bind(R.id.ly_page2)
    RelativeLayout lyPage2;
    @Bind(R.id.vp_scroll)
    ViewPager vpScroll;
    @Bind(R.id.sl_root)
    ScrollableLayout slRoot;
    @Bind(R.id.pfl_root)
    PtrClassicFrameLayout pflRoot;
    @Bind(R.id.hourly_forecast_recycler_view)
    RecyclerView hourlyForecastRecyclerView;
    @Bind(R.id.recycler_card)
    CardView recyclerCard;
    @Bind(R.id.title_cond_icon)
    ImageView titleCondIcon;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.title_layout)
    LinearLayout titleLayout;
    @Bind(R.id.floatingToolbar)
    FloatingToolbar floatingToolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;

    private float titleMaxScrollHeight;
    private float hearderMaxHeight;
    private float avatarTop;
    private float maxScrollHeight;
    private WeatherPresenter weatherPresenter;
    private SharedPreferenceUtil sharedPreferenceUtil;
    private Bus bus;
    private HourlyWeatherAdapter hourlyWeatherAdapter;
    private List<Fragment> fragmentList;
    private SingleFragmentPagerAdapter pagerAdapter;
    private ProgressDialog progressDialog;
    private BDLocationListener locationListener;
    private LocationClient locationClient;
    private String searchCityName;
    private NetStatusReceiver netStatusReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        registerNetworkReceiver();
        initView();
        initSearchView();
    }

    private void initSearchView() {

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchCityName = query;
                weatherPresenter.queryCity(query);
                progressDialog = ProgressDialog.show(HomeActivity.this, "", "正在查询...", true, true);
                progressDialog.setCanceledOnTouchOutside(false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewOpened() {
                // Do something once the view is open.
            }

            @Override
            public void onSearchViewClosed() {
                // Do something once the view is closed.
            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Do something when the suggestion list is clicked.
                String suggestion = searchView.getSuggestionAtPosition(position);
                searchCityName = suggestion;
                //searchView.setQuery(suggestion, false);
                weatherPresenter.queryCity(suggestion);
                searchView.closeSearch();
                progressDialog = ProgressDialog.show(HomeActivity.this, "", "正在查询...", true, true);
                progressDialog.setCanceledOnTouchOutside(false);
            }
        });

        searchView.adjustTintAlpha(0.8f);

    }

    private void initView() {
        floatingToolbar.setClickListener(this);
        floatingToolbar.attachFab(fab);

        titleLayout.setTranslationY(-1000);
        slRoot.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
            @Override
            public void onScroll(int translationY, int maxY) {
                floatingToolbar.hide();
                translationY = -translationY;
                if (titleMaxScrollHeight == 0) {
                    titleMaxScrollHeight = ((View) titleLayout.getParent()).getBottom() - titleLayout.getTop();
                    maxScrollHeight = hearderMaxHeight + titleMaxScrollHeight;
                }
                if (hearderMaxHeight == 0) {
                    hearderMaxHeight = cityText.getTop();
                    maxScrollHeight = hearderMaxHeight + titleMaxScrollHeight;
                }
                if (avatarTop == 0) {
                    avatarTop = cityText.getTop();
                }
                titleLayout.setTranslationY(Math.max(0, maxScrollHeight + translationY));
            }
        });

        pflRoot.setEnabledNextPtrAtOnce(true);
        pflRoot.setLastUpdateTimeRelateObject(this);
        pflRoot.setPtrHandler(this);
        pflRoot.setKeepHeaderWhenRefresh(true);

        fragmentList = new ArrayList<>();

        sharedPreferenceUtil = SharedPreferenceUtil.getInstance();
        weatherPresenter = new WeatherPresenter(HomeActivity.this, this);
        bus = EventBus.getInstance();
        bus.register(this);
        weatherPresenter.start();

        pagerAdapter = new SingleFragmentPagerAdapter(getSupportFragmentManager());
        fragmentList.add(DailyForecastWeatherFragment.getInstance());
        fragmentList.add(SuggestionFragment.getInstance());
        vpScroll.setAdapter(pagerAdapter);
        vpScroll.addOnPageChangeListener(this);
        slRoot.getHelper().setCurrentScrollableContainer((ScrollableHelper.ScrollableContainer) fragmentList.get(0));

        hourlyForecastRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //weatherPresenter.loadWeatherDataById(ConstantData.CITY_ID);

        if (NetworkUtil.isConnected(this)) {
            if (sharedPreferenceUtil.getBoolean(SharedPreferenceUtil.IS_READY, false)) {
                String cityName = sharedPreferenceUtil.getString(SharedPreferenceUtil.CITY_NAME, ConstantData.CITY_NAME);
                weatherPresenter.loadWeatherDataByName(cityName);
                ConstantData.CITY_NAME = cityName;
            } else {
                locate();
            }
        } else {
            NetworkUtil.noNetworkAlert(this);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        searchView.clearSuggestions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchView.activityResumed();
        String[] arr = getResources().getStringArray(R.array.suggestions);

        searchView.addSuggestions(arr);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
        weatherPresenter.stop();
        searchView.clearHistory();
        unregisterReceiver(netStatusReceiver);
    }

    private void registerNetworkReceiver() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        netStatusReceiver = new NetStatusReceiver();
        registerReceiver(netStatusReceiver, intentFilter);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        if (vpScroll.getCurrentItem() == 0 && slRoot.isCanPullToRefresh()) {
            return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
        }
        return false;
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        if (NetworkUtil.isConnected(this)) {
            weatherPresenter.loadWeatherDataByName(ConstantData.CITY_NAME);
        } else {
            NetworkUtil.noNetworkAlert(this);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        floatingToolbar.hide();
    }

    @Override
    public void onPageSelected(int position) {

        slRoot.getHelper().setCurrentScrollableContainer((ScrollableHelper.ScrollableContainer) fragmentList.get(position));
        if (position == 0) {
            lyPage1.setBackgroundResource(R.drawable.rectangle_left_select);
            tvPage1.setTextColor(Color.parseColor("#ffffff"));
            lyPage2.setBackgroundResource(R.drawable.rectangle_right);
            tvPage2.setTextColor(Color.parseColor("#435356"));
        } else {
            lyPage1.setBackgroundResource(R.drawable.rectangle_left);
            tvPage1.setTextColor(Color.parseColor("#435356"));
            lyPage2.setBackgroundResource(R.drawable.rectangle_right_select);
            tvPage2.setTextColor(Color.parseColor("#ffffff"));
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.tv_right, R.id.ly_page1, R.id.ly_page2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                break;
            case R.id.ly_page1:
                vpScroll.setCurrentItem(0);
                break;
            case R.id.ly_page2:
                vpScroll.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void showLoadingView() {

        progressDialog = ProgressDialog.show(this, "", "拼命加载中...", true, true);
        progressDialog.setCanceledOnTouchOutside(false);

    }

    @Override
    public void hideLoadingView() {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        pflRoot.refreshComplete();

    }

    @Override
    public void showWeatherData(WeatherItem weatherItem) {

        bus.post(new DailyForecastWeather(weatherItem.getDaily_forecast()));
        bus.post(weatherItem.getSuggestion());

        NowWeather nowWeather = weatherItem.getNow();
        BasicInfo basic = weatherItem.getBasic();

        titleCondIcon.setImageResource(sharedPreferenceUtil.getInt(nowWeather.getCond().getCode(), R.mipmap.e999));
        titleText.setText(String.format("%s %s℃", basic.getCity(), nowWeather.getTmp()));
        cityText.setText(basic.getCity());
        nowWeatherTxt.setText(nowWeather.getCond().getTxt());
        codeIcon.setImageResource(sharedPreferenceUtil.getInt(nowWeather.getCond().getCode(), R.mipmap.e999));
        tempText.setText(String.format("%s℃", nowWeather.getTmp()));
        flHumText.setText(String.format("体感温度: %s℃    湿度: %s%%", nowWeather.getFl(), nowWeather.getHum()));

        hourlyWeatherAdapter = new HourlyWeatherAdapter(weatherItem.getHourly_forecast(), this);
        hourlyForecastRecyclerView.setAdapter(hourlyWeatherAdapter);
    }


    @Override
    public void showError(String error) {

        pflRoot.refreshComplete();
        showToast(error);

    }

    @Override
    public void showVerifyMessage(VerifyMessage message) {

        if (message.isOk()) {
            weatherPresenter.loadWeatherDataByName(searchCityName);
            saveCityName(searchCityName);
            ConstantData.CITY_NAME = searchCityName;
            showToast("设置成功");
        } else {
            showToast(message.getMessage());
        }

    }

    public void showToast(String s) {

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_city:
                if (NetworkUtil.isConnected(this)) {
                    searchView.openSearch();
                } else {
                    NetworkUtil.noNetworkAlert(this);
                }

                break;
            case R.id.action_feedback:
                break;
            case R.id.action_location:
                if (NetworkUtil.isConnected(this)) {
                    locate();
                } else {
                    NetworkUtil.noNetworkAlert(this);
                }

                break;
            case R.id.action_trend:
                Intent intent = new Intent(this, WeatherTrendActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onItemLongClick(MenuItem item) {

    }

    public class SingleFragmentPagerAdapter extends FragmentPagerAdapter {

        public SingleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getCount() > position ? fragmentList.get(position) : null;
        }

        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }
    }

    private void locate() {
        progressDialog = ProgressDialog.show(this, "", "正在定位...", true, true);
        progressDialog.setCanceledOnTouchOutside(false);

        locationListener = new MyLocationListener();
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(locationListener);

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setAddrType("all");// 返回的定位结果包含地址信息
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02

        option.setScanSpan(500000);// 设置发起定位请求的间隔时间为5000ms

        option.disableCache(true);// 禁止启用缓存定位

        option.setPoiNumber(5); // 最多返回POI个数
        option.setPoiDistance(1000); // poi查询距离
        option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
        locationClient.setLocOption(option);
        locationClient.start();
    }

    public void saveCityName(String city_name) {

        sharedPreferenceUtil.putString(SharedPreferenceUtil.CITY_NAME, city_name);
        sharedPreferenceUtil.putBoolean(SharedPreferenceUtil.IS_READY, true);

    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation result) {
            if (result != null) {
                //Logger.i(result.getAddrStr());
                locationClient.stop();
                String location = getCityName(result.getAddrStr());
                ConstantData.CITY_NAME = location;
                weatherPresenter.loadWeatherDataByName(location);
                saveCityName(location);
                showToast(result.getAddrStr());
                //currentCity = result.getCity();
                //currentAddress = result.getAddrStr();
            } else {
                showToast("定位不成功");
            }
        }

        @Override
        public void onReceivePoi(BDLocation result) {

        }
    }

    public String getCityName(String s) {
        String cityName = "新蔡";
        if (s.contains("省") && s.contains("市")) {
            cityName = s.substring(s.indexOf("省") + 1, s.indexOf("市"));
        } else if (s.contains("市")) {
            cityName = s.substring(0, s.indexOf("市"));
        }
        Logger.i("当前城市：", cityName);

        return cityName;
    }

    @Override
    public void onBackPressed() {

        if (searchView.isOpen()) {
            searchView.closeSearch();
        } else if (floatingToolbar.isShowing()) {
            floatingToolbar.hide();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
