package com.example.guyazran.broadcastreceiversrecap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by guyazran on 7/20/15.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String s = "Broadcast Received in MyBroadcastReceiver: " + intent.getStringExtra("key");
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }
}
