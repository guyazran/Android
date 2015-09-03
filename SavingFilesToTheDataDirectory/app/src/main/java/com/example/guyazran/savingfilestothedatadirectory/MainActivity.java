package com.example.guyazran.savingfilestothedatadirectory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File cacheDir = getCacheDir();
        File file = new File(cacheDir, "textfile.txt");

        //writing to file (creates file if doesn't exist or overwrites the existing file)
        try {
            //FileOutputStream fileOutputStream = openFileOutput("textfile.txt", MODE_PRIVATE);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write("again, we are learning today about writing to files.");
            outputStreamWriter.close();
            fileOutputStream.close(); //perhaps its enough to close the writer
            Toast.makeText(MainActivity.this, "File Saved Successfully", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Log.d("Guy", "error saving file: " + e.getMessage());
        } catch (IOException ex){
            Log.d("Guy", "error writing to file: " + ex.getMessage());
        }

        //reading from file
        try {
            //FileInputStream fileInputStream = openFileInput("textfile.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            char[] chars = new char[1024];
            int actuallyRead = inputStreamReader.read(chars);
            inputStreamReader.close();
            fileInputStream.close();
            String s = new String(chars, 0, actuallyRead);
            Toast.makeText(MainActivity.this, "S=" + s, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.d("Guy", "error: " + e.getMessage());
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
