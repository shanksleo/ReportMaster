package com.example.shanks.reportmaster;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Calendar;

public class WeatherService extends Service {
    private String weatherGson;
    private String w2;
//    public WeatherService() {
//    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
//    onStartCommand代替 onStart 使用.
//    实现 service 的标准模式就是.在 onStartCommand 创建和运行一个像线程,用来后台处理
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Boolean flag =  sharedPreferences.getBoolean("my_notify",false);

        if (flag) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    updateWeather();
                }
            }).start();
        }
//        启动定时任务These allow you to schedule your application to be run at some point in the future.
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 0);

        Intent intent1 = new Intent(WeatherService.this,AutoUpdateBroadCast.class);

        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent1, 0);
        manager.set(AlarmManager.RTC,calendar.getTimeInMillis(),pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }



    private void updateWeather(){
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
                        Weather weather = new Gson().fromJson(weatherGson,Weather.class);
                        w2 = weather.weatherinfo.weather2;
                        if (w2.indexOf("雨") != -1){
                            Notification.Builder mBuilder = new Notification.Builder(YoApplication.getContext());
                            mBuilder.setSmallIcon(R.drawable.i001);
                            mBuilder.setTicker("你点我啊");
                            mBuilder.setContentTitle("好消息好消息");
                            mBuilder.setContentText("你明天要带伞了");
                            Notification notification =  mBuilder.build();
                            NotificationManager notificationManager = (NotificationManager)YoApplication.getContext().getSystemService(YoApplication.getContext().NOTIFICATION_SERVICE);
                            notificationManager.notify(0x122,notification);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("abc", error.getMessage(), error);
            }
        });
        queue.add(jsonObjectRequest);

    }

}
