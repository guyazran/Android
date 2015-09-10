package com.example.guyazran.animatingviews;

import java.util.ArrayList;

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
    private ArrayList<Ball> balls;


    public MoveBallThread(int screenHeight, int screenWidth, MoveBallListener listener, ArrayList<Ball> balls) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        ballTopMargin = 0;
        ballLeftMargin = 0;
        this.listener = listener;
        go = true;
        this.balls = balls;
    }

    public void stopThread(){
        go = false;
    }

    @Override
    public void run() {
        while (go) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < balls.size(); i++) {
                Ball currentBall = balls.get(i);
                currentBall.setxPos(currentBall.getxPos() + currentBall.getVelocityX());
                currentBall.setyPos(currentBall.getyPos() + currentBall.getVelocityY());

                if (listener != null)
                    listener.onBallPositionChanged(i);

                //change direction when hitting another ball;
                for (int j = 0; j < balls.size(); j++) {
                    Ball checkBall = balls.get(j);
                    if (checkBall == currentBall){
                        continue;
                    }
                    if (currentBall.distanceFromBall(checkBall) <= 100){
                        int tempx = checkBall.getVelocityX();
                        int tempy = checkBall.getVelocityY();
                        checkBall.setVelocityX(currentBall.getVelocityX());
                        checkBall.setVelocityY(currentBall.getVelocityY());
                        currentBall.setVelocityX(tempx);
                        currentBall.setVelocityY(tempy);

//                        if (((currentBall.getVelocityX() < 0 && checkBall.getVelocityX() >= 0) ||
//                                (currentBall.getVelocityX() >= 0 && checkBall.getVelocityX() < 0)) &&
//                                ((currentBall.getVelocityY() < 0 && checkBall.getVelocityY() >= 0) ||
//                                        (currentBall.getVelocityX() >= 0 && checkBall.getVelocityX() < 0))){
//
//                        }
//                        else if ((currentBall.getVelocityX() < 0 && checkBall.getVelocityX() >= 0) ||
//                                (currentBall.getVelocityX() >= 0 && checkBall.getVelocityX() < 0)){
//                            checkBall.setVelocityX(checkBall.getVelocityX() * -1);
//                            currentBall.setVelocityX(currentBall.getVelocityX() * -1);
//                        }
//                        else if ((currentBall.getVelocityY() < 0 && checkBall.getVelocityY() >= 0) ||
//                                (currentBall.getVelocityX() >= 0 && checkBall.getVelocityX() < 0)){
//                            checkBall.setVelocityY(checkBall.getVelocityY() * -1);
//                            currentBall.setVelocityY(currentBall.getVelocityY() * -1);
//                        }

                        listener.onBallCollision();
                    }
                }

                //change direction when hitting the wall
                if (currentBall.getxPos() - 50 <= 0 || currentBall.getxPos() + 50 >= screenWidth) {
                    currentBall.setVelocityX(currentBall.getVelocityX() * -1);
                }
                if (currentBall.getyPos() - 50 <= 0 || currentBall.getyPos() + 50 >= screenHeight) {
                    currentBall.setVelocityY(currentBall.getVelocityY() * -1);
                }
            }
        }
    }

    public static interface MoveBallListener{
        void onBallPositionChanged(int ball);
        void onBallCollision();
    }
}
