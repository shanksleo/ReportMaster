package com.example.shanks.reportmaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoUpdateBroadCast extends BroadcastReceiver {
    public AutoUpdateBroadCast() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent intent2 = new Intent(context,WeatherService.class);
        context.startService(intent2);
    }
}
