package com.example.guyazran.usingtimepicker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.TokenWatcher;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class MainActivity extends ActionBarActivity {

    TimePicker timePicker;

    Toast t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
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

    public void btnClick(View view) {

        /*
        final int currentHour = timePicker.getCurrentHour();
        final int currentMinute = timePicker.getCurrentMinute();
        String selectedTime = "";
        if (currentHour < 10){
            selectedTime += "0";
        }
        selectedTime += currentHour + ":";
        if (currentMinute < 10)
            selectedTime += "0";
        selectedTime += currentMinute;

        if (t != null)
            t.cancel();
        t = Toast.makeText(this, selectedTime, Toast.LENGTH_LONG);
        t.show();
        */

        NumberFormat formatter = new DecimalFormat("00");
        Toast.makeText(this, formatter.format(timePicker.getCurrentHour()) + ":" +
                formatter.format(timePicker.getCurrentMinute()), Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, DateActivity.class);
        startActivity(intent);
    }
}
