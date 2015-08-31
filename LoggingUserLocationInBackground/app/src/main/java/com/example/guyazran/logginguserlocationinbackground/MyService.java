package com.example.guyazran.logginguserlocationinbackground;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by guyazran on 8/31/15.
 */
public class MyService extends Service {

    private ServiceThread serviceThread;
    int counter = 0;
    private final IBinder binder = new LocalBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        //startServiceThread();

        return START_STICKY;
    }

    public void startServiceThread(){
        if (serviceThread == null){
            serviceThread = new ServiceThread();
            serviceThread.start();
        }
    }

    public void stopServiceThread(){
        if (serviceThread != null){
            serviceThread.go = false;
            serviceThread.interrupt();
            serviceThread = null;
        }
    }

    public void test(){
        Log.d("Guy", "test");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopServiceThread();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    private class ServiceThread extends Thread{

        boolean go = true;

        @Override
        public void run() {
            while (go){
                Log.d("Guy", "service thread..." + counter++);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class LocalBinder extends Binder {

        MyService getService(){
            return MyService.this;
        }
    }

}
