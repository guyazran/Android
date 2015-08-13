package com.example.guyazran.monitoringthestateofthecall;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    TelephonyManager telephonyManager;
    MyPhoneStateListener myPhoneStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPhoneStateListener = new MyPhoneStateListener(this);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

    }

    void onStopListening(View view){
        telephonyManager.listen(PhoneBroadcastReceiver.myPhoneStateListener, PhoneStateListener.LISTEN_NONE);
        PhoneBroadcastReceiver.alreadyListening = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //putting in onCreate and onDestroy will allow listening in background while app is still open
        //telephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //telephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_NONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
