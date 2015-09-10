package com.example.guyazran.animatingviews;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements MoveBallThread.MoveBallListener {

    RelativeLayout layout;
    int layoutHeight;
    int layoutWidth;
    ArrayList<Ball> balls;;
    ArrayList<ImageView> ballViews;
    RelativeLayout.LayoutParams layoutParams;
    Handler handler;
    MoveBallThread moveBallThread;
    private int nextBallType = 0;
    private long addBallDelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        balls = new ArrayList<Ball>();
        ballViews = new ArrayList<ImageView>();
        layoutParams = new RelativeLayout.LayoutParams(100, 100);

        handler = new Handler();
        layout = (RelativeLayout) findViewById(R.id.layout);
        ViewTreeObserver viewTreeObserver = layout.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layoutHeight = layout.getHeight();
                layoutWidth = layout.getWidth();
                ViewTreeObserver viewTreeObserver = layout.getViewTreeObserver();
                viewTreeObserver.removeOnGlobalLayoutListener(this);
                moveBallThread = new MoveBallThread(layoutHeight, layoutWidth, MainActivity.this, balls);
                moveBallThread.start();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (layoutHeight != 0 && layoutWidth != 0){
            moveBallThread = new MoveBallThread(layoutHeight, layoutWidth, MainActivity.this,balls);
            moveBallThread.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        moveBallThread.stopThread();

        moveBallThread.interrupt();
        moveBallThread = null;
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


    @Override
    public void onBallPositionChanged(final int ball) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                balls.get(ball).moveToNewPosition(ballViews.get(ball));
            }
        });
    }

    @Override
    public void onBallCollision() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(MainActivity.this, "ball collision", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btnAddBall(View view) {
        if (addBallDelay == 0 || System.nanoTime() - addBallDelay > 200000000 ) {
            addBall();
            setBallViewImage();
            layout.addView(ballViews.get(ballViews.size() - 1), new LinearLayout.LayoutParams(100, 100));
            addBallDelay = System.nanoTime();
        }
    }

    private void addBall(){
        if (nextBallType == 7){
            nextBallType = 0;
        }
        balls.add(new Ball(nextBallType));
        nextBallType++;
        ballViews.add(new ImageView(MainActivity.this));
    }

    private void setBallViewImage(){
        switch (balls.get(balls.size()-1).getType()){
            case 0:
                ballViews.get(ballViews.size() - 1).setImageResource(R.drawable.red_ball);
                break;
            case 1:
                ballViews.get(ballViews.size() - 1).setImageResource(R.drawable.tennis_ball);
                break;
            case 2:
                ballViews.get(ballViews.size() - 1).setImageResource(R.drawable.soccer_ball);
                break;
            case 3:
                ballViews.get(ballViews.size() - 1).setImageResource(R.drawable.black_ball);
                break;
            case 4:
                ballViews.get(ballViews.size() - 1).setImageResource(R.drawable.beach_ball);
                break;
            case 5:
                ballViews.get(ballViews.size() - 1).setImageResource(R.drawable.checkered_ball);
                break;
            case 6:
                ballViews.get(ballViews.size() - 1).setImageResource(R.drawable.eight_ball);
                break;
        }

    }
}
