package com.example.guyazran.linkingactivities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public static final int REQUEST_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = new Intent("com.example.guyazran.linkingactivities.SecondActivity");
        //startActivity(Intent.createChooser(intent, "Choose an application"));

        //Intent intent = new Intent(this, SecondActivity.class);
        //startActivity(intent);



    }

    public void onClick(View view){
        Intent intent = new Intent("com.example.guyazran.linkingactivities.SecondActivity");
        intent.putExtra("str1", "this is a string");
        intent.putExtra("age1", 25);



        Bundle extras = new Bundle();
        extras.putString("str2", "this is another string");
        extras.putInt("age2", 35);

        intent.putExtras(extras);

        Dog d = new Dog("snoopy", "cool");
        intent.putExtra("dog", d);

        //startActivity(intent);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                Toast.makeText(this, Integer.toString(data.getIntExtra("age3" , 0)), Toast.LENGTH_LONG).show();
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
