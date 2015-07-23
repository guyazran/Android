package com.example.guyazran.usingtimepicker;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class DateActivity extends ActionBarActivity {

    DatePicker datePicker;

    Toast t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        datePicker = (DatePicker)findViewById(R.id.datePicker);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_date, menu);
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
        NumberFormat formatter = new DecimalFormat("00");

        if (t != null)
            t.cancel();
        t = Toast.makeText(this, formatter.format(datePicker.getDayOfMonth()) + "/" +
                formatter.format(datePicker.getMonth() + 1) + "/" +
                datePicker.getYear() % 100, Toast.LENGTH_LONG);
        t.show();
    }
}
