package com.example.guyazran.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
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

    private Bitmap downloadImage(String url){
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            inputStream = openHttpGetConnection(url);
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception ex){

        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    public String readJSONFeed(String url){
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200){
                HttpEntity httpEntity = response.getEntity();
                InputStream inputStream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line);
                }
                inputStream.close();
            }else {
                Log.d("Guy", "status code is not OK");
            }
        }catch (Exception ex){
            Log.d("Guy", "error: " + ex.getMessage());
        }
        return stringBuilder.toString();
    }

    public void btnDownloadImage(View view) {
        String url = "http://orig13.deviantart.net/649b/f/2013/209/c/2/minion_png_by_isammyt-d6fn0fj.png";
        new DownloadImageTask().execute(url);
    }

    public void btnReadWeather(View view) {
        new ReadWeatherJSONFeedTask().execute("http://api.openweathermap.org/data/2.5/weather?q=telaviv,il");
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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImage(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView img = (ImageView)findViewById(R.id.image);
            img.setImageBitmap(bitmap);
        }
    }

    private class ReadWeatherJSONFeedTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            return readJSONFeed(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject main = new JSONObject(jsonObject.getString("main"));
                String temp = main.getString("temp");
                Toast.makeText(getBaseContext(), "temp = " + temp, Toast.LENGTH_LONG).show();
            }catch (Exception ex){

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
