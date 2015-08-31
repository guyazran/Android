package com.example.guyazran.logginguserlocationinbackground;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MyService myService;
    boolean bound = false;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.LocalBinder binder = (MyService.LocalBinder)service;
            myService = binder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindMyService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindMyService();
    }

    void readAndWriteInDB(){
        DBAdapter dbAdapter = new DBAdapter(this);
        exampleOfWritingToDB(dbAdapter);
        exampleOfReadingFromDB(dbAdapter);
    }

    void exampleOfWritingToDB(DBAdapter dbAdapter){
        try {
            dbAdapter.open();
            dbAdapter.insertLocation(123, 456);
            dbAdapter.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void exampleOfReadingFromDB(DBAdapter dbAdapter){
        try {
            dbAdapter.open();
            Cursor cursor = dbAdapter.getAllLocations();
            while (cursor.moveToNext()){
                long time = cursor.getLong(0);
                double lat = cursor.getDouble(1);
                double lng = cursor.getDouble(cursor.getColumnIndex(dbAdapter.KEY_LNG));
                Log.d("Guy", "location time=" + time + " lat=" + lat + " lng=" + lng);
            }
            cursor.close();
            dbAdapter.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private void bindMyService() {
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbindMyService() {
        if (bound){
            unbindService(serviceConnection);
            bound = false;
        }
    }

    public void btnStartService(View view) {
        bindMyService();
        startService(new Intent(this, MyService.class));
    }

    public void btnStopService(View view) {
        unbindMyService();
        stopService(new Intent(this, MyService.class));
    }



    public void btnStartThread(View view) {
        if (bound)
            myService.startServiceThread();
    }

    public void btnStopThread(View view) {
        if (bound)
            myService.stopServiceThread();
    }
}
