package com.example.guyazran.destinationalert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

/**
 * Created by guyazran on 8/27/15.
 */
public class MyLocationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra(LocationManager.KEY_PROVIDER_ENABLED)){
            if (!intent.getBooleanExtra(LocationManager.KEY_PROVIDER_ENABLED, true)){
                Toast.makeText(context, "Provider disabled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Provider enabled", Toast.LENGTH_LONG).show();
            }
        }
        if (intent.hasExtra(LocationManager.KEY_LOCATION_CHANGED)){
            Location location = (Location)intent.getExtras().get(LocationManager.KEY_LOCATION_CHANGED);
            Toast.makeText(context, "Location Changed: lat:" + location.getLatitude() + " lon" +
                    location.getLongitude(), Toast.LENGTH_LONG).show();
        }
    }
}
