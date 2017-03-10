package com.example.nik.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Nik on 10.03.2017.
 */

public class ScreenOnReceiver extends BroadcastReceiver {
    private final String TAG = "Receiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent curIntent = new Intent(context, LoadService.class);
        context.startService(curIntent);
        Log.d(TAG, "onReceive: " + intent.getAction());
    }
}
