package com.example.guyazran.multiplecountdownviews;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CountdownThread.CountdownListener {

    LinearLayout linearLayout;
    int counter = 0;
    TextView[] textViews;
    boolean firstTimer = true;
    CountdownThread countdownThread;
    Handler handler;
    int timerCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
        textViews = new TextView[10];
        for (int i = 0; i < textViews.length; i++) {
            textViews[i] = (TextView) linearLayout.findViewWithTag(String.valueOf(i+1));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countdownThread.stopThread();
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

    public void btnStartTimer(View view) {
        if (timerCounter < 10) {
            if (countdownThread == null || !countdownThread.isRunning()) {
                firstTimer = false;
                countdownThread = new CountdownThread(this, timerCounter);
                countdownThread.start();
            } else {
                countdownThread.addTimer();
            }
            timerCounter++;
        }
    }

    @Override
    public void onCount(int position, int count) {

        class CountRunnable implements Runnable{

            int position;
            int count;

            public CountRunnable(int position, int count) {
                this.position = position;
                this.count = count;
            }

            @Override
            public void run() {
                textViews[position].setText(String.valueOf(count));
            }
        }

        handler.post(new CountRunnable(position, count));
    }

    @Override
    public void onFinishCountdown(int finishedTimer) {

        class FinishCountRunnable implements Runnable{
            int finishedTimer;

            public FinishCountRunnable(int finishedTimer) {
                this.finishedTimer = finishedTimer;
            }

            @Override
            public void run() {
                Toast.makeText(getBaseContext(), "Timer: " + finishedTimer + " has finished", Toast.LENGTH_SHORT).show();
            }
        }

        handler.post(new FinishCountRunnable(finishedTimer));
    }
}
