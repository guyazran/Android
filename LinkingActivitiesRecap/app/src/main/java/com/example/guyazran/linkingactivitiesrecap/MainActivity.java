package com.example.guyazran.linkingactivitiesrecap;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public static final int REQUEST_CODE = 1;
    public static final String STR_1 = "str1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Intent intent = new Intent("SecondActivity");
        startActivity(intent);
        */

        /*
        Intent intent = new Intent();
        intent.setAction("SecondActivity");
        startActivity(intent);
        */

        /*
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
        */

        /*
        Intent intent = new Intent("SecondActivity");
        startActivity(Intent.createChooser(intent, "Choose an application"));
        */
    }

    public void btnClick(View view) {
        Intent intent = new Intent("SecondActivity");
        intent.putExtra(STR_1, "this is a string");
        intent.putExtra("age1", 25);

        Bundle extras = new Bundle();
        extras.putString("str2", "this ia another string");
        extras.putInt("age2", 80);

        intent.putExtras(extras);

        Dog d = new Dog("snoopy", 7);
        intent.putExtra("myObject", d);

        //startActivity(intent);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                Toast.makeText(this, Integer.toString(data.getIntExtra("age3", 0)), Toast.LENGTH_LONG).show();
            }
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
