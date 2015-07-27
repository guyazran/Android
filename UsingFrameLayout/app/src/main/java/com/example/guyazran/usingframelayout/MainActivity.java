package com.example.guyazran.usingframelayout;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    int count = 1;
    FrameLayout frameLayout;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout)findViewById(R.id.frameLayout);
        imageView = (ImageView) frameLayout.findViewWithTag(String.valueOf(count));
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

    public void btnNext(View view) {
        imageView.setVisibility(View.INVISIBLE);
        count++;
        if (count == 5)
            count = 1;
        imageView = (ImageView) frameLayout.findViewWithTag(String.valueOf(count));
        imageView.setVisibility(View.VISIBLE);
    }

    public void btnBack(View view) {
        imageView.setVisibility(View.INVISIBLE);
        count--;
        if (count == 0)
            count = 4;
        imageView = (ImageView) frameLayout.findViewWithTag(String.valueOf(count));
        imageView.setVisibility(View.VISIBLE);
    }
}
