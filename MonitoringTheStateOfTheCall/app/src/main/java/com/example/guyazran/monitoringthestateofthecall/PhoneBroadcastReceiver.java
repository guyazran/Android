package com.example.guyazran.monitoringthestateofthecall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by guyazran on 8/13/15.
 */
public class PhoneBroadcastReceiver extends BroadcastReceiver {

    static MyPhoneStateListener myPhoneStateListener;
    static boolean alreadyListening = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "state changed", Toast.LENGTH_LONG).show();
        if (!alreadyListening && 1==2){
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            myPhoneStateListener = new MyPhoneStateListener(context);
            telephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_NONE);
            alreadyListening = true;
        }
    }
}
