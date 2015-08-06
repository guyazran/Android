package com.example.guyazran.learninglistview;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashSet;

public class MainActivity extends ListActivity {

    HashSet<String> selectedCities;
    ArrayAdapter<String> adapter;

    //LinearLayout lstCities;

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
            "Jerusalem",
            "Netanya",
            "Hadera"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities));

        ListView lstCities = getListView();
        lstCities.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, cities);
        setListAdapter(adapter);

        selectedCities = new HashSet<String>();

        /*
        lstCities = (LinearLayout)findViewById(R.id.lstCities);

        for (int i = 0; i < cities.length; i++) {
            TextView txtCity = new TextView(this);
            txtCity.setText(cities[i]);
            txtCity.setTag(i);
            txtCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getBaseContext(), ((TextView) v).getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

            lstCities.addView(txtCity, LinearLayout.LayoutParams.MATCH_PARENT, 100);

        }
        */
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //Toast.makeText(this, "You have selectied: " + cities[position], Toast.LENGTH_LONG).show();
        CheckedTextView item = (CheckedTextView)v;
        String city = cities[position];
        if (item.isChecked())
            selectedCities.add(city);
        else
            selectedCities.remove(city);
//        if (!selectedCities.remove(city))
//            selectedCities.add(city);
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

    public void printToLogCat(View view) {
        Log.d("Guy", "selected cities: " + selectedCities.toString());
        cities[0] += "!";
        adapter.notifyDataSetInvalidated();
    }
}
