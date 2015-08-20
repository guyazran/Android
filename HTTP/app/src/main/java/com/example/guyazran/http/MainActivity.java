package com.example.guyazran.http;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public static InputStream openHttpGetConnection(String url) throws Exception{
        InputStream inputStream = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
        inputStream = httpResponse.getEntity().getContent();
        return inputStream;
    }

    public String downloadText(String url){
        int BUFFER_SIZE = 1024;
        InputStream inputStream = null;
        try {
            inputStream = openHttpGetConnection(url);
        } catch (Exception ex){
            return "error";
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
            while((charRead = inputStreamReader.read(inputBuffer)) > 0){
                String readString = new String(inputBuffer, 0, charRead);
                str += readString;
            }
        }catch (IOException ex){

        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public void btnDownloadText(View view) {
        String url = "http://www.webservicex.net/currencyconvertor.asmx/ConversionRate?FromCurrency=USD&ToCurrency=ILS";
        new DownloadTextTask().execute(url);
    }


    private class DownloadTextTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            return downloadText(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
            Log.d("Guy", s);
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
