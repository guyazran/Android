package com.example.guyazran.learninglistview2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

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
            "Tiberius",
            "Katsrin",
            "Rehovot",
            "Lod",
            "Jerusalem",
            "Netanya",
            "Hadera"
    };
    private String[] citiesStatus = {
            "non Stop City",
            "Ramat Gan",
            "Rishon Lezion",
            "the city of the future",
            "tel aviv HaSharon",
            "pearl of HaSharon",
            "Hertselya",
            "Haifa",
            "the harbor city",
            "Ashkelon",
            "the Vegas of Israel",
            "the lake city",
            "Katsrin",
            "Rehovot",
            "the crime city",
            "the holy city",
            "the mafia city",
            "Hadera"
    };
    private int[] images = {
            R.drawable.smiley_1,
            R.drawable.smiley_2,
            R.drawable.smiley_3,
            R.drawable.smiley_4,
            R.drawable.filmax,
            R.drawable.motorola
    };

//    private ListItem[] cityList = {
//        new ListItem("Tel Aviv", "non Stop City", R.drawable.smiley_1),
//        new ListItem("Ramat Gan", "Ramat Gan", 0),
//        new ListItem("Rishon Lezion", "Rishon Lezion", 0),
//        new ListItem("Modi'in", "The city of the future", R.drawable.smiley_2),
//        new ListItem("Kfar Saba", "tel aviv hasharon", R.drawable.smiley_3),
//        new ListItem("Ra'anana", "the pearl of HaSharon", R.drawable.smiley_4),
//        new ListItem("Hertselya", "Hertselya", 0),
//        new ListItem("Haifa", "Haifa", 0),
//        new ListItem("Ashdod", "The harbor city", R.drawable.motorola),
//        new ListItem("Ashkelon", "Ashkelon", R.drawable.filmax),
//        new ListItem("Eilat", "The Vegas of Israel", R.drawable.smiley_1),
//        new ListItem("Tiberius", "The lake city", R.drawable.smiley_2),
//        new ListItem("Katsrin", "Katsrin", 0),
//        new ListItem("Rehovot", "Rehovot", 0),
//        new ListItem("Lod", "The crime city", R.drawable.smiley_3),
//        new ListItem("Jerusalem", "The holy City", R.drawable.smiley_4),
//        new ListItem("Netanya", "The mafia city", R.drawable.motorola),
//        new ListItem("Hadera", "Hadera", 0),
//    };

    private ListItem[] listItems;

    ListView listView1, listView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.whatsapp_contact_item, R.id.txtContactName,cities);
        //CustomArrayAdapter adapter = new CustomArrayAdapter(this, R.layout.whatsapp_contact_item, R.id.txtContactName,
        //        R.id.txtContactStatus, R.id.imgContact, cities, citiesStatus, images);

        listItems = new ListItem[cities.length];
        for (int i = 0; i < cities.length; i++) {
            listItems[i] = new ListItem(cities[i], citiesStatus[i], i<images.length ? images[i] : 0);
        }

        //ItemArrayAdapter adapter = new ItemArrayAdapter(this, R.layout.whatsapp_contact_item, R.id.txtContactName,
        //        R.id.txtContactStatus, R.id.imgContact, listItems);

        AdvancedArrayAdapter adapter = new AdvancedArrayAdapter(this, R.layout.whatsapp_contact_item, R.id.txtContactName,
                R.id.txtContactStatus, R.id.imgContact, listItems);
        listView1 = (ListView)findViewById(R.id.listView1);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "You have Selected item: " + cities[position], Toast.LENGTH_LONG).show();
            }
        });

        /*
        listView2 = (ListView)findViewById(R.id.listView2);
        listView2.setAdapter(adapter);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "You have Selected item: " + cities[position], Toast.LENGTH_LONG).show();
            }
        });
        */


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
