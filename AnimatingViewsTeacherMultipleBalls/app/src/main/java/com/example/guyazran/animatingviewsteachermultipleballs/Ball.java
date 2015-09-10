package com.example.guyazran.animatingviewsteachermultipleballs;

import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by guyazran on 9/10/15.
 */
public class Ball {
    private int x, y;
    private int velocityX, velocityY;

    private int color;

    private View view;
    private RelativeLayout.LayoutParams layoutParams;

    private RelativeLayout layout;

    public Ball(int x, int y, int velocityX, int velocityY, int color) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.color = color;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        this.view.setBackgroundColor(this.color);
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public RelativeLayout.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public void setLayoutParams(RelativeLayout.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }

    public void flipXdirection(){
        velocityX *= -1;
    }

    public void flipYdirection(){
        velocityY *= -1;
    }

    public void move(){
        x += velocityX;
        y += velocityY;
    }

    public void updateView(){
        this.layoutParams.setMargins(x, y, 0, 0);
        this.view.setLayoutParams(this.layoutParams);
    }

    public void setLayout(RelativeLayout layout) {
        this.layout = layout;
        this.view = new View(this.layout.getContext());
        this.view.setBackgroundColor(this.color);
        this.layoutParams = new RelativeLayout.LayoutParams(100, 100);
        updateView();
        layout.addView(this.view, this.layoutParams);
    }
}
