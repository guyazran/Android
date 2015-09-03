package com.example.guyazran.multiplecountdownviews;

import android.widget.TextView;

/**
 * Created by guyazran on 9/3/15.
 */
public class CountdownThread extends Thread {
    int position;
    CountdownListener listener;
    int counter = 60;

    public CountdownThread(int position, CountdownListener listener) {
        this.position = position;
        this.listener = listener;
    }

    @Override
    public void run() {
        while (counter>0){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (listener != null)
                listener.onCount(position, --counter);
        }
        if (listener != null){
            listener.onFinishCountdown(position + 1);
        }
    }

    public static interface CountdownListener{
        void onCount(int position, int count);
        void onFinishCountdown(int finishedTimer);
    }
}
