package com.example.guyazran.sharedpreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TEMPERATURE = "temperature";
    public static final String AUTHENTICATED = "authenticated";
    public static final String USERNAME = "username";
    SharedPreferences sharedPreferences;
    public static final String PREF_NAME = "MyPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(TEMPERATURE, 34.0f);
        editor.putBoolean(AUTHENTICATED, true);
        editor.putString(USERNAME, "leon");
        editor.commit();

        //readSharedPreferences();
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);

/*

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String userName = sharedPreferences.getString(USERNAME, "");
        TextView lblUserName = (TextView)findViewById(R.id.lblUserName);
        lblUserName.setText(userName);
*/
        }

    public void readSharedPreferences(){
        float temperature = sharedPreferences.getFloat(TEMPERATURE, 0);
        boolean authenticated = sharedPreferences.getBoolean(AUTHENTICATED, false);
        String username = sharedPreferences.getString(USERNAME, "");

        Log.d("Guy", "temperature=" + temperature + " authenticated=" + authenticated + " username=" + username);
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

    public void btnSaveUserName(View view) {
        EditText txtUserName = (EditText)findViewById(R.id.txtUserName);
        String userName = txtUserName.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, userName);
        editor.commit();
    }
}
