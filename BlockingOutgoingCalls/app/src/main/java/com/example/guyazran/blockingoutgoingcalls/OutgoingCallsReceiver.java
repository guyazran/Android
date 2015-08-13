package com.example.guyazran.blockingoutgoingcalls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by guyazran on 8/13/15.
 */
public class OutgoingCallsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String outgoingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        if (outgoingNumber.contains("12345")){
            setResultData(null);
            Toast.makeText(context, "This call is not allowed", Toast.LENGTH_LONG).show();
        }
    }
}
