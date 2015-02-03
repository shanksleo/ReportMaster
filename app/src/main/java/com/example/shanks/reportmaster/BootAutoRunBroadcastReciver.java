package com.example.shanks.reportmaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootAutoRunBroadcastReciver extends BroadcastReceiver {
    /*要接收的intent源*/
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    public BootAutoRunBroadcastReciver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (intent.getAction().equals(ACTION))
        {
            Intent intent2 = new Intent(context,WeatherService.class);
            context.startService(intent2);
            //这边可以添加开机自动启动的应用程序代码
        }
    }
}
