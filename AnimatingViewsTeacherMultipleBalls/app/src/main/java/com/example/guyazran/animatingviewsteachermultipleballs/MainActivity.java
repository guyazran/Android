package com.example.guyazran.animatingviewsteachermultipleballs;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends Activity {

    RelativeLayout layout;
    int layoutHeight;
    int layoutWidth;
    //View ball;
    Thread thread;
    boolean go = false;
    Handler handler = new Handler();
    boolean ballCreated = false;
    //RelativeLayout.LayoutParams layoutParams;
    //int velocityX = 8, velocityY = 14;
    //int x = 20, y = 30;
    ArrayList<Ball> balls;
    final static int[] colors = {Color.GREEN, Color.RED, Color.BLUE, Color.CYAN, Color.GRAY, Color.BLACK};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (RelativeLayout)findViewById(R.id.layout);
        balls = new ArrayList<Ball>();
        ViewTreeObserver viewTreeObserver = layout.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layoutHeight = layout.getHeight();
                layoutWidth = layout.getWidth();
                ViewTreeObserver viewTreeObserver = layout.getViewTreeObserver();
                viewTreeObserver.removeOnGlobalLayoutListener(this);
                //Log.d("Elad", "in onGlobalLayout()");
                addBall(20, 30, 8, 14, colors[0]);
                ballCreated = true;
            }
        });


    }

    private void addBall(int x, int y, int velocityX, int velocityY, int color){
        Ball ball = new Ball(x, y, velocityX, velocityY, color);
        ball.setLayout(layout);
        balls.add(ball);
    }

    @Override
    protected void onResume() {
        super.onResume();
        go = true;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(MainActivity.this.go){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {

                    }
                    if(ballCreated) {
                        for (final Ball ball : balls) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ball.updateView();
                                }
                            });
                            if (ball.getX() >= layoutWidth - 100 || ball.getX() <= 0)
                                ball.flipXdirection();
                            if (ball.getY() >= layoutHeight - 100 || ball.getY() <= 0)
                                ball.flipYdirection();
                            ball.move();
                        }
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        go = false; //primitive types are copied between thread ??

        thread.interrupt();
        thread = null;

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

    public void btnAddBall(View view) {
        addBall(20, 30, 8, 14, colors[balls.size() % colors.length]);
    }
}