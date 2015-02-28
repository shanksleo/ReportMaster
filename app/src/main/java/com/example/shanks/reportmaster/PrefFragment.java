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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        加载设置文件界面
        addPreferencesFromResource(R.xml.preference);
//
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Boolean flah =  sharedPreferences.getBoolean("my_notify",false);
        Log.d("abc","被修改"+ flah);
        //获得网络请求队列
        RequestQueue queue =  Volley.newRequestQueue(YoApplication.getContext());
//      最新可用天气 上海 http://weather.51wnl.com/weatherinfo/GetMoreWeather?cityCode=101020100&weatherType=0
//      最新可用天气 杭州 http://weather.51wnl.com/weatherinfo/GetMoreWeather?cityCode=101210101&weatherType=0
//        weather 1,今天当前天气  weather2 明天天气
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://weather.51wnl.com/weatherinfo/GetMoreWeather?cityCode=101020100&weatherType=0", null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("abc", response.toString());
                        weatherGson = response.toString();
                        //使用 Gson 解析Json
                        Weather weather = new Gson().fromJson(weatherGson,Weather.class);
                        Toast.makeText(YoApplication.getContext(),weather.weatherinfo.city+"今天天气是"+weather.weatherinfo.weather1,Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("abc", error.getMessage(), error);
            }
        });
        queue.add(jsonObjectRequest);

        Intent intent = new Intent(YoApplication.getContext(),WeatherService.class);
        YoApplication.getContext().startService(intent);


    }


}
