package com.example.guyazran.countdownteachersolution;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int NUMBER_OF_COUNTERS = 10;
    TextView[] countDownLabels;
    CountDownThread countDownThread;
    long[] startTimes = new long[NUMBER_OF_COUNTERS];
    int pos = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layoutCountDown = (LinearLayout) findViewById(R.id.layoutCountDown);
        countDownLabels = new TextView[NUMBER_OF_COUNTERS];
        for (int i = 0; i < NUMBER_OF_COUNTERS; i++) {
            countDownLabels[i] = new TextView(this);
            countDownLabels[i].setText("60");
            countDownLabels[i].setTextSize(30);
            layoutCountDown.addView(countDownLabels[i], LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            startTimes[i] = -1;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        countDownThread = new CountDownThread(handler, startTimes, countDownLabels);
        countDownThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        countDownThread.go = false;

        //optional;
        countDownThread.interrupt();
        countDownThread = null;//ensures that the garbage collector will come for the thread sooner
    }

    public void btnClick(View view) {
        if (pos<NUMBER_OF_COUNTERS) {
            startTimes[pos++] = System.currentTimeMillis();
        }
    }

    static class CountDownThread extends Thread{

        private boolean go = true;
        private Handler handler;
        private long[] startTimes;
        private TextView[] countDownLabels;
        public CountDownThread(Handler handler, long[] startTimes, TextView[] countDownLabels){
            this.handler = handler;
            this.startTimes = startTimes;
            this.countDownLabels = countDownLabels;
        }

        @Override
        public void run() {
            while (go){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        long currentTime = System.currentTimeMillis();
                        for (int i = 0; i < MainActivity.NUMBER_OF_COUNTERS; i++) {
                            if (startTimes[i] != -1) {
                                int elapsedTime = 60 - (int) ((currentTime - startTimes[i]) / 1000l);
                                if (elapsedTime >= 0) {
                                    countDownLabels[i].setText(String.valueOf(elapsedTime));
                                } else {
                                    startTimes[i] = -1l;
                                }
                            }
                        }
                    }
                });
            }
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
}
