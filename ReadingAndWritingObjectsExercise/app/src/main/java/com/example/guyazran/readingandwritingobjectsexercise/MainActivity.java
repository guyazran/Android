package com.example.guyazran.readingandwritingobjectsexercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    public static final String DOG_TXT = "Dog.txt";
    EditText txtNameOfDog;
    EditText txtBirthYearOfDog;
    Dog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNameOfDog = (EditText)findViewById(R.id.txtNameOfDog);
        txtBirthYearOfDog = (EditText)findViewById(R.id.txtBirthYearOfDog);
        JSONObject dogAsJsonObject = null;

        try {
            FileInputStream fileInputStream = openFileInput(DOG_TXT);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            char[] chars = new char[1024];
            int actuallyRead;
            StringBuilder readString = new StringBuilder("");
            while ((actuallyRead = inputStreamReader.read(chars)) > -1){
                String s = new String(chars);
                readString.append(s);
            }
            dogAsJsonObject = new JSONObject(readString.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (dogAsJsonObject != null){
            try {
                String name = dogAsJsonObject.getString("name");
                int birthYear = dogAsJsonObject.getInt("birthYear");
                d = new Dog(name, birthYear);
                txtNameOfDog.setText(d.getName());
                txtBirthYearOfDog.setText(String.valueOf(d.getBirthYear()));
            } catch (JSONException e) {
                e.printStackTrace();
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

    public void btnSaveDog(View view) {
        d.setName(txtNameOfDog.getText().toString());
        d.setBirthYear(Integer.valueOf(txtBirthYearOfDog.getText().toString()));


        JSONObject dogAsJsonObject = new JSONObject();;

        try {
            dogAsJsonObject.put("name", d.getName());
            dogAsJsonObject.put("birthYear", d.getBirthYear());
            FileOutputStream fileOutputStream = openFileOutput(DOG_TXT, MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(dogAsJsonObject.toString());
            outputStreamWriter.close();
            fileOutputStream.close();
            Toast.makeText(MainActivity.this, "Dog Saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
