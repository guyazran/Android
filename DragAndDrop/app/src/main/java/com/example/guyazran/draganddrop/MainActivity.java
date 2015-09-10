package com.example.guyazran.draganddrop;

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    RelativeLayout layout;
    ImageView imageView;
    RelativeLayout dropZone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (RelativeLayout) findViewById(R.id.layout);
        imageView = (ImageView) findViewById(R.id.imageView);
        dropZone = (RelativeLayout) findViewById(R.id.dropZone);
        layout.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                final int action = event.getAction();
                View view = ((View) event.getLocalState());
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("Guy", "layout drag started");
                        break;
                    case DragEvent.ACTION_DROP:
                        layoutParams.leftMargin = (int) event.getX();
                        layoutParams.topMargin = (int) event.getY();
                        view.setLayoutParams(layoutParams);
                        Log.d("Guy", "layout dropped");
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        //view.setVisibility(View.VISIBLE);
                        Log.d("Guy", "layout drag ended");
                        return true;
                }

                return false;
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData clipData = ClipData.newPlainText("", "");
                    View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
                    v.startDrag(clipData, dragShadowBuilder, v, 0);
                    v.setVisibility(View.INVISIBLE);
                    return true;
                }
                return false;
            }
        });

        dropZone.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                final int action = event.getAction();
                View view = ((View) event.getLocalState());
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("Guy", "dropzone drag started");
                        break;
                    case DragEvent.ACTION_DROP:
                        layout.removeView(view);
                        dropZone.addView(view);
                        Log.d("Guy", "dropzone dropped");
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        view.setVisibility(View.VISIBLE);

                        Log.d("Guy", "dropzone drag ended");
                        return true;
                }

                return true;
            }
        });
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
}
