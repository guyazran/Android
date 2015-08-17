package com.example.guyazran.interceptingincommingsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by guyazran on 8/17/15.
 */
public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = null;
        String str = "SMS from: ";
        if (bundle != null){
            Object[] pdus = (Object[])bundle.get("pdus");
            messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                if (i == 0){
                    str += messages[i].getOriginatingAddress();
                    str += ": ";
                }
                str += messages[i].getMessageBody();
            }
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();
            abortBroadcast();
        }
    }
}
