package com.jc.heweather.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by HaohaoChang on 2016/3/19.
 */
public class NetStatusReceiver extends BroadcastReceiver {

    public static boolean isConnected = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
            Toast.makeText(context, "网络连接不可用", Toast.LENGTH_SHORT).show();
            isConnected = false;
        } else {
            int networkType = activeNetworkInfo.getType();
            if (networkType == ConnectivityManager.TYPE_WIFI) {
                Toast.makeText(context,"正在使用wifi连接", Toast.LENGTH_SHORT).show();
                isConnected = true;
            } else if (networkType == connectivityManager.TYPE_MOBILE) {
                Toast.makeText(context, "正在使用移动网络", Toast.LENGTH_SHORT).show();
                isConnected = true;

            }
        }
    }

}
