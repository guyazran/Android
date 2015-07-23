package com.example.guyazran.autocompletetextview;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


public class MainActivity extends ActionBarActivity {

    private String[] cities = {
            "Tel Aviv",
            "Ramat Gan",
            "Rishon Lezion",
            "Modi'in",
            "Kfar Saba",
            "Ra'anana",
            "Hertselya",
            "Haifa",
            "Ashdod",
            "Ashkelon",
            "Eilat",
            "Tiverius",
            "Katsrin",
            "Rehovot",
            "Lod",
            "Jerusalem"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, cities);

        AutoCompleteTextView textView = (AutoCompleteTextView)findViewById(R.id.txtCity);

        textView.setThreshold(1);

        textView.setAdapter(adapter);
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
