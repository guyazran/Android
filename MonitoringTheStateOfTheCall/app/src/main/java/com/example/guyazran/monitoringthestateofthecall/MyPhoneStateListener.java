package com.example.guyazran.monitoringthestateofthecall;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by guyazran on 8/13/15.
 */
public class MyPhoneStateListener extends PhoneStateListener {

    Context context;

    public MyPhoneStateListener(Context context) {
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {

        String stateName = "";
        switch (state){
            case TelephonyManager.CALL_STATE_IDLE:
                stateName = "Idle";
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                stateName = "Off Hook";
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                stateName = "Ringing";
                break;
        }

        Toast.makeText(context, "onCallStateChanged state = " + stateName + "incomming number = " + incomingNumber, Toast.LENGTH_LONG).show();
    }
}
