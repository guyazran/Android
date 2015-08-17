package com.example.guyazran.autoansweringcalls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;

/**
 * Created by guyazran on 8/17/15.
 */
public class IncommingCallsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String extraState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        //this string tells us the state of the phone
        //there are three states: off hook, ringing, idle;

        if (extraState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if (incomingNumber.contentEquals("12345")) {
                //at this point we know there is an incomming call from 12345
                //we want to accept the incomming call, as if the user swiped their finger on the green button to answer
                Intent answerIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
                answerIntent.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
                context.sendOrderedBroadcast(answerIntent, "android.permission.CALL_PRIVILEGED");
            }
        }
    }
}
