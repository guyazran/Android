package com.example.guyazran.destinationalert;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;

    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, MyLocationReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, pendingIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, ReachedLocationActivity.class), 0);
//        locationManager.addProximityAlert(32.084653, 34.800971, 200, -1, pendingIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        locationManager.removeProximityAlert(pendingIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(pendingIntent);
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
