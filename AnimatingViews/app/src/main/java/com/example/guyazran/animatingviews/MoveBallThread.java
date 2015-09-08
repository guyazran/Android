package com.example.guyazran.animatingviews;

/**
 * Created by guyazran on 9/7/15.
 */
public class MoveBallThread extends Thread {

    private int horizontalSpeed = 10;
    private int verticalSpeed = 10;
    private int screenHeight;
    private int screenWidth;
    private int ballTopMargin;
    private int ballLeftMargin;
    private MoveBallListener listener;
    private boolean go;

    public MoveBallThread(int screenHeight, int screenWidth, int ballTopMargin, int ballLeftMargin, MoveBallListener listener) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.ballTopMargin = ballTopMargin;
        this.ballLeftMargin = ballLeftMargin;
        this.listener = listener;
        go = true;
    }

    public void stopThread(){
        go = false;
    }

    @Override
    public void run() {
        while (go) {
            try {
                Thread.sleep(1000 / 30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ballLeftMargin += horizontalSpeed;
            ballTopMargin += verticalSpeed;
            if (listener != null)
                listener.onBallPositionChanged(ballLeftMargin, ballTopMargin);
            if (ballLeftMargin <= 0 || ballLeftMargin + 100 >= screenWidth){
                horizontalSpeed *= -1;
            }
            if (ballTopMargin <= 0 || ballTopMargin + 100 >= screenHeight){
                verticalSpeed *= -1;
            }
        }
    }

    public static interface MoveBallListener{
        void onBallPositionChanged(int width, int height);
    }
}
