package com.example.shanks.reportmaster;

import android.app.Application;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by shanks on 15/1/30.
 */
public class PrefFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{
    private String weatherGson;
    private String w2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        加载设置文件界面
        addPreferencesFromResource(R.xml.preference);
//       自定义 sharedpreference 文件名字
//        getPreferenceManager().setSharedPreferencesName("my_setting");
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
//当任何一个按钮被点击,那么这个方法都会被触发
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        SwitchPreference switchPreference = (SwitchPreference) findPreference("my_notify");
        Log.d("abc","被点击");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());





        return super.onPreferenceTreeClick(preferenceScreen,preference);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Boolean flah =  sharedPreferences.getBoolean("my_notify",false);
        Log.d("abc","被修改"+ flah);
        //获得网络请求队列
        RequestQueue queue =  Volley.newRequestQueue(YoApplication.getContext());
//      上海http://www.weather.com.cn/data/cityinfo/101020100.html
//      杭州http://www.weather.com.cn/data/cityinfo/101210101.html
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://www.weather.com.cn/data/cityinfo/101210101.html", null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("abc", response.toString());
                       // Toast.makeText(YoApplication.getContext(),response.toString(),Toast.LENGTH_LONG).show();
                       // Toast.makeText(YoApplication.getContext(),response.toString(),Toast.LENGTH_LONG).show();
                        weatherGson = response.toString();
                        //使用 Gson 解析Json
                        WeatherSmile weather = new Gson().fromJson(weatherGson,WeatherSmile.class);
                      //  Toast.makeText(YoApplication.getContext(),weatherGson,Toast.LENGTH_LONG).show();
                        Toast.makeText(YoApplication.getContext(),"今天天气是"+weather.weatherinfo.weather,Toast.LENGTH_LONG).show();
//                        Toast.makeText(YoApplication.getContext(),"今天天气是"+weather.weatherinfo.weather.indexOf("雨") ,Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("abc", error.getMessage(), error);
            }
        });
        queue.add(jsonObjectRequest);

//        Notification.Builder mBuilder = new Notification.Builder(YoApplication.getContext());
//        mBuilder.setSmallIcon(R.drawable.i001);
//        mBuilder.setTicker("你点我试试");
//        mBuilder.setContentTitle("好消息好消息");
//        mBuilder.setContentText("你明天要带伞了");
////        pendingIntent执行的操作实质上是参数传进来的Intent的操作
////        PendingIntent intent = new PendingIntent();
////        Intent intent1 = new Intent();
////        intent1.setAction("");
////        mBuilder.setContentIntent();
////
//        Notification notification =  mBuilder.build();
//        NotificationManager notificationManager = (NotificationManager)YoApplication.getContext().getSystemService(YoApplication.getContext().NOTIFICATION_SERVICE);
//        notificationManager.notify(0x122,notification);

        Intent intent = new Intent(YoApplication.getContext(),WeatherService.class);
        YoApplication.getContext().startService(intent);


    }

    public void notifyTop(){


    }


}
