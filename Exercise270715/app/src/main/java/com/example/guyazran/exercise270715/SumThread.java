package com.example.guyazran.exercise270715;

/**
 * Created by guyazran on 7/27/15.
 */
public class SumThread extends Thread {

    int num1;
    int num2;
    private SumListener listener;

    public SumThread(int num1, int num2, SumListener listener){
        this.num1 = num1;
        this.num2 = num2;
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (listener != null)
            listener.sum(num1 + num2);

    }

    public static interface SumListener{
        void sum(int result);
    }
}
