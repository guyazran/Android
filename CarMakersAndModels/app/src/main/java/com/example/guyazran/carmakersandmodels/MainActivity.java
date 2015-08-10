package com.example.guyazran.carmakersandmodels;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CarMaker[] carsAndModels = {
            new CarMaker("Toyota", new String[]{"Corrola", "Rav4", "Hilux", "Land Cruiser"}),
            new CarMaker("Hyundai", new String[]{"i30", "accent", "i10", "Volvster", "Getz"}),
            new CarMaker("Renault", new String[]{"Megan", "Clio", "Fluence", "Latitude", "Kangoo"}),
            new CarMaker("Peugeot", new String[]{"107", "206", "RCZ", "308", "208"}),
            new CarMaker("Honda", new String[]{"Civic", "Accord", "Jazz", "Fit", "Pilot"}),
            new CarMaker("Ford", new String[]{"GT", "Mondeo", "Focus", "Fiesta", "Mustang"}),
            new CarMaker("Subaru", new String[]{"B4", "Forester", "Legacy", "Outback", "WRX"})
    };
    private ListView lstMakers, lstModels;
    private int makerPosition;

    private Toast t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstMakers = (ListView)findViewById(R.id.lstMakers);
        lstModels = (ListView)findViewById(R.id.lstModels);
        ArrayAdapter<CarMaker> makerAdapter = new ArrayAdapter<CarMaker>(this, android.R.layout.simple_list_item_1, carsAndModels);
        lstMakers.setAdapter(makerAdapter);
        lstMakers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                makerPosition = position;
                ArrayAdapter<String> modelAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1,
                        carsAndModels[position].getModels());
                lstModels.setAdapter(modelAdapter);
            }
        });
        lstModels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (t!=null)
                    t.cancel();
                String maker = carsAndModels[makerPosition].getName();
                String model = carsAndModels[makerPosition].getModels()[position];
                t = Toast.makeText(getBaseContext(), "You have Selected: " + maker +
                        " " + model , Toast.LENGTH_LONG);
                t.show();
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
