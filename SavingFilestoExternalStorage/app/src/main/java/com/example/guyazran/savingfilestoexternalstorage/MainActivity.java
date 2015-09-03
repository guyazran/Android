package com.example.guyazran.savingfilestoexternalstorage;

import android.os.Environment;
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

    //will return true if the external storage is readable & writeable.
    boolean isExternalStorageAvailable(){
        boolean externalStorageAvailable = false;
        boolean externalStorageWritable = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            //you can read and write to the media
            externalStorageAvailable = externalStorageWritable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY == state){
            //you can only read from the media
            externalStorageAvailable = true;
        }else {
            //you cannot read nor write to the media
            externalStorageAvailable = externalStorageWritable = false;
        }
        return externalStorageAvailable && externalStorageWritable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //writing to files
        if (isExternalStorageAvailable()){
            File externalStorage = getExternalFilesDir(null);
            File file = new File(externalStorage, "textfile.txt");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                outputStreamWriter.write("now we write files to external storage");
                outputStreamWriter.flush();
                outputStreamWriter.close();
                fileOutputStream.close();
                Toast.makeText(MainActivity.this, "fille saved successfully", Toast.LENGTH_LONG).show();
            } catch (java.io.IOException e) {
                e.getStackTrace();
            }
        }

        //reading from external storage;
        if(isExternalStorageAvailable()){ //this method checks if the external storage is writable, which is not necessary for reading
            File externalStorage = getExternalFilesDir("Music"); //null means the root
            File file = new File(externalStorage, "textfile.txt");
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                char[] chars = new char[1024];
                StringBuilder readString = new StringBuilder("");
                int actuallyRead;
                while ((actuallyRead = inputStreamReader.read(chars)) > 0){
                    readString.append(chars, 0, actuallyRead);
                }
                Toast.makeText(MainActivity.this, "content: " + readString, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
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
}
