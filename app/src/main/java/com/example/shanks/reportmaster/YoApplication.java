package com.example.shanks.reportmaster;

import android.app.Application;
import android.content.Context;

/**
 * Created by shanks on 15/1/30.
 */
public class YoApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
