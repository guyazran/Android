package com.example.guyazran.logginguserlocationinbackground;

import android.database.Cursor;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBAdapter dbAdapter = new DBAdapter(this);
        exampleOfWritingToDB(dbAdapter);
        exampleOfReadingFromDB(dbAdapter);
    }

    void exampleOfWritingToDB(DBAdapter dbAdapter){
        try {
            dbAdapter.open();
            dbAdapter.insertLocation(123, 456);
            dbAdapter.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void exampleOfReadingFromDB(DBAdapter dbAdapter){
        try {
            dbAdapter.open();
            Cursor cursor = dbAdapter.getAllLocations();
            while (cursor.moveToNext()){
                long time = cursor.getLong(0);
                double lat = cursor.getDouble(1);
                double lng = cursor.getDouble(cursor.getColumnIndex(dbAdapter.KEY_LNG));
                Log.d("Guy", "location time=" + time + " lat=" + lat + " lng=" + lng);
            }
            cursor.close();
            dbAdapter.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
