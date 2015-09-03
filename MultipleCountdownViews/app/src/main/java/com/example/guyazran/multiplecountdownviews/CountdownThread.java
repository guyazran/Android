package com.example.guyazran.multiplecountdownviews;

import android.util.Log;
import android.widget.TextView;

/**
 * Created by guyazran on 9/3/15.
 */
public class CountdownThread extends Thread {
    private int activeTimers;
    private Timer[] timers;
    private CountdownListener listener;
    private boolean go = true;
    private boolean running = false;
    private int startIndex;

    public CountdownThread(CountdownListener listener, int startFrom) {
        this.activeTimers = 1;
        this.startIndex = startFrom;
        this.listener = listener;
        timers = new Timer[10 - startFrom];
        for (int i = 0; i < timers.length; i++) {
            timers[i] = new Timer();
        }
        activeTimers = 1;
    }

    public void stopThread(){
        go = false;
    }

    public void addTimer(){
        if (activeTimers <timers.length)
            activeTimers++;
    }

    @Override
    public void run() {
        Log.d("Countdown Thread", "Thread has started");
        running = true;
        while (timers[activeTimers-1].count > 0 && go){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < activeTimers; i++) {
                if (listener != null) {
                    if (timers[i].count > 0) {
                        if (timers[i].sleepCount == 1000) {
                            timers[i].count--;
                            timers[i].sleepCount = 0;
                            listener.onCount(i + startIndex, timers[i].count);
                        }else {
                            timers[i].sleepCount+=100;
                        }
                    }else {
                        if (!timers[i].finished) {
                            listener.onFinishCountdown(i + startIndex + 1);
                            timers[i].finished = true;
                        }
                    }
                }
            }
        }
        running = false;
        if (listener != null)
            listener.onFinishCountdown(activeTimers + startIndex);
        Log.d("Countdown Thread", "Thread has finished");
    }

    public boolean isRunning(){
        return running;
    }

    public static interface CountdownListener{
        void onCount(int position, int count);
        void onFinishCountdown(int finishedTimer);
    }

    private class Timer{
        int count = 60;
        int sleepCount = 0;
        boolean finished = false;
    }
}
