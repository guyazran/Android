package com.example.guyazran.recordingaudio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnStartRecording;
    AudioRecorder audioRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStartRecording = (Button) findViewById(R.id.btnStartRecording);
        audioRecorder = new AudioRecorder();
        File externalStorageDir = getExternalFilesDir(null);
        audioRecorder.setExternalStorageDir(externalStorageDir);
    }

    public void btnStartRecording(View view) {
        if (!audioRecorder.isRecording()){
            audioRecorder.start();
            btnStartRecording.setText("stop recording");
        } else {
            audioRecorder.stop();
            btnStartRecording.setText("start recording");
        }
    }
    public void btnPlayRecording(View view) {
        audioRecorder.play();
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
