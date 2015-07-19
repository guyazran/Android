package com.example.guyazran.sendingandreceivingbroadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by guyazran on 7/16/15.
 */
public class MyBroadcastReceiver2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "in onReceive2: " + intent.getStringExtra("key"), Toast.LENGTH_LONG).show();
    }
}
