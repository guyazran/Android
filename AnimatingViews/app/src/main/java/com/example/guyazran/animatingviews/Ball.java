package com.example.guyazran.animatingviews;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by guyazran on 9/8/15.
 */
public class Ball {

    private int xPos, yPos, type;
    private int velocityX, velocityY;
    private RelativeLayout.LayoutParams position;

    public Ball(int type) {
        this.type = type;
        xPos=50;
        yPos=50;
        Random random = new Random();
        velocityX=random.nextInt(10) + 1;
        velocityY=random.nextInt(10) + 1;
        position = new RelativeLayout.LayoutParams(100, 100);
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public int getType() {
        return type;
    }

    public void moveToNewPosition(ImageView ballView){
        position.setMargins(xPos - 50, yPos - 50, 0, 0);
        ballView.setLayoutParams(position);
    }

    public double distanceFromBall(Ball ball){
        return distanceFromBall(ball.xPos, ball.yPos);
    }

    public double distanceFromBall(double x, double y){
        return Math.sqrt(sumOfSquares(x, y));
    }

    private double sumOfSquares(double x, double y){
        double deltaX = x - xPos;
        double deltaY = y - yPos;
        return deltaX*deltaX + deltaY*deltaY;
    }

    public void changeVelocitiesAfterCollision(Ball ball){
//        double pA, pB;
//        double angleA, angleB;
//        double velocityA, velocityB;
//        velocityA = distanceFromBall(velocityX, velocityY);
//        velocityB = distanceFromBall(ball.velocityX, ball.velocityY);
//        if (velocityY == 0) {
//            if (velocityX >= 0){
//                angleA = 0;
//            } else {
//                angleA = 180;
//            }
//        } else {
//            angleA = Math.toDegrees(Math.atan(Math.tan(Math.toRadians(velocityX / velocityY))));
//        }
//        if (ball.velocityY == 0) {
//            if (ball.velocityX >= 0){
//                angleB = 0;
//            } else {
//                angleB = 180;
//            }
//        } else {
//            angleB = Math.toDegrees(Math.atan(Math.tan(Math.toRadians(ball.velocityX / ball.velocityY))));
//        }

//        double contactAngle = Math.toDegrees(Math.atan((velocityX)/(velocityY)));
//        double newXspeed1 = 2;
//        double newYspeed1 = 2;
//        double newX


    }
}
