package com.example.guyazran.sendingandreceivingbroadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by guyazran on 7/16/15.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "in onReceive: " + intent.getStringExtra("key"), Toast.LENGTH_LONG).show();
        abortBroadcast();
    }
}
