package com.example.guyazran.activitylifecycle;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int xFromBundle = -2;
        if(savedInstanceState != null) {
            xFromBundle = savedInstanceState.getInt("x", -1);
        }

        Log.d("Guy", "in onCreate()" + x + " xFromBundle: " + xFromBundle);
        x = 10;

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Guy", "in onStart()" + x);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Guy", "in onResume()" + x);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Guy", "in onStop()" + x);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Guy", "in onDestroy()" + x);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Guy", "in onPause()" + x);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("x", x);
        super.onSaveInstanceState(outState);
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
