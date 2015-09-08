package com.example.guyazran.animatingviews;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;

public class MainActivity extends Activity implements MoveBallThread.MoveBallListener {

    RelativeLayout layout;
    int layoutHeight;
    int layoutWidth;
    ImageView ball;
    RelativeLayout.LayoutParams layoutParams;
    Handler handler;
    MoveBallThread moveBallThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File file = new File(getExternalFilesDir(null), "textfile.txt");

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
                ball = new ImageView(MainActivity.this);
                ball.setImageResource(R.drawable.red_ball);
                layoutParams = new RelativeLayout.LayoutParams(100, 100);
                layoutParams.setMargins(0, 0, 0, 0);
                layout.addView(ball, layoutParams);
                ball.setLayoutParams(layoutParams);
                moveBallThread = new MoveBallThread(layoutHeight, layoutWidth, layoutParams.leftMargin,
                        layoutParams.topMargin, MainActivity.this);
                moveBallThread.start();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (layoutHeight != 0 && layoutWidth != 0){
            moveBallThread = new MoveBallThread(layoutHeight, layoutWidth, layoutParams.leftMargin,
                    layoutParams.topMargin, MainActivity.this);
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
    public void onBallPositionChanged(final int width, final int height) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                layoutParams.setMargins(width, height, 0, 0);
                ball.setLayoutParams(layoutParams);
            }
        });
    }
}
