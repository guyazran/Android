package com.example.guyazran.myweatherapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String[] cities = {
            "Jerusalem, IL",
            "Moscow, RU",
            "New York City, US",
            "Madrid, ES",
            "Cairo, EG",
            "Johannesburg, ZA",
            "Berlin, DE",
            "Brussels",
            "Paris, FR",
            "London, GB",
            "Boston, US",
            "Los Angeles, US",
            "Copenhagen, DK",
            "Stockholm, SE",
            "Bern, CH",
            "Bangkok, TH",
            "Istanbul, TR",
            "Anthens, GR",
            "Mexico City, MX",
            "Ottawa, CA",
            "Beijing, CN",
            "Bogota, CO",
            "Helsinki, FI",
            "Oslo, NO",
            "Sofia,BG",
            "Lisbon, PT",
            "Canberra, AU",
            "Addis Ababa, ET",
            "Khartoum, SD",
            "Warsaw, PL",
            "Bucharest, RO",
            "Amman, JO",
            "Wellington, NZ",
            "Nuuk, GL",
            "San Salvador, SV",
            "Taipei, TW",
            "Tokyo, JP",
            "Nairobi, KE",
            "Budapest, HU",
            "Guatemala City, GT",
            "Tallinn, EE",
            "Suva, FJ",
            "Quito, EC",
            "Jakarta, ID",
            "Hanoi, VN"
    };

    AutoCompleteTextView actChooseCity;
    TextView lblCityName, lblTemp, lblDescrpition;
    ImageView imgWeather;

    URL sunImgURL, fogImgURL, cloudImgURL, rainImgURL, snowImgURL, sunAndCloudImgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actChooseCity = (AutoCompleteTextView)findViewById(R.id.actChooseCity);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, cities);
        actChooseCity.setThreshold(1);
        actChooseCity.setAdapter(adapter);
        actChooseCity.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        actChooseCity.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_ENTER:
                            btnGetWeather(null);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        lblCityName = (TextView)findViewById(R.id.lblCityName);
        lblTemp = (TextView)findViewById(R.id.lblTemp);
        lblDescrpition = (TextView)findViewById(R.id.lblDescription);
        imgWeather = (ImageView)findViewById(R.id.imgWeather);

        try {
            sunImgURL = new URL("https://upload.wikimedia.org/wikipedia/commons/thumb/0/0d/O.sun.png/480px-O.sun.png");
            fogImgURL = new URL("http://www.theburialgrounds.com/Memorialobjects/Fog.png");
            cloudImgURL = new URL("http://pngimg.com/upload/cloud_PNG2.png");
            rainImgURL = new URL("http://cliparts.co/cliparts/pi7/K46/pi7K46AxT.png");
            snowImgURL = new URL("http://www.clker.com/cliparts/n/W/z/O/t/O/snow-clouds-hi.png");
            sunAndCloudImgUrl = new URL("http://icons.iconarchive.com/icons/large-icons/large-weather/512/partly-cloudy-day-icon.png");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String readJSONFee(URL url){
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            int responseCode= urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line);
                }
                urlConnection.disconnect();
            }else {
                Log.d("Guy", "status code is not OK");
            }
        }catch (Exception ex){
            Log.d("Guy", "error: " + ex.getMessage());
        }
        return stringBuilder.toString();
    }

    private Bitmap downloadImage(URL url){
        Bitmap bitmap = null;
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            inputStream = urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception ex){

        }
        finally {
            urlConnection.disconnect();
        }
        return bitmap;
    }

    class ReadWeatherInfoTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... params) {
            return readJSONFee(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject weatherInfo = new JSONObject(s);
                if(weatherInfo.getString("cod").equals("200")){
                    lblCityName.setText(weatherInfo.getString("name"));
                    JSONObject main = new JSONObject(weatherInfo.getString("main"));
                    //String temp = main.getString("temp");
                    //Toast.makeText(getBaseContext(), "temp = " + temp, Toast.LENGTH_LONG).show();
                    lblTemp.setText("Temperature: " + main.getString("temp") + "°C");

                    JSONArray weatherArr = new JSONArray(weatherInfo.getString("weather"));
                    JSONObject weatherObj = weatherArr.getJSONObject(0);
                    String description = weatherObj.getString("description");
                    lblDescrpition.setText(description);

                    description = description.toLowerCase();
                    if (description.contains("clear"))
                        new DownloadWeatherImageTask().execute(sunImgURL);
                    else if (description.contains("rain"))
                        new DownloadWeatherImageTask().execute(rainImgURL);
                    else if (description.contains("snow"))
                        new DownloadWeatherImageTask().execute(snowImgURL);
                    else if (description.contains("fog")||description.contains("mist"))
                        new DownloadWeatherImageTask().execute(fogImgURL);
                    else if (description.contains("cloud"))
                        new DownloadWeatherImageTask().execute(cloudImgURL);
                    else
                        new DownloadWeatherImageTask().execute(sunAndCloudImgUrl);
                }else if(weatherInfo.getString("cod").equals("404")) {
                    lblCityName.setText("City Not Found");
                    lblTemp.setText("");
                } else {
                    lblCityName.setText("An error has occured");
                    lblTemp.setText("");
                }
            }catch (Exception ex){

            }
        }
    }

    class DownloadWeatherImageTask extends AsyncTask<URL, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(URL... params) {
            return downloadImage(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imgWeather.setImageBitmap(bitmap);
        }
    }

    public void btnGetWeather(View view) {
        String chosenCity = actChooseCity.getText().toString();

        if (chosenCity.equals("")){
            Toast.makeText(this, "Please enter a name of a city in the world", Toast.LENGTH_LONG).show();
        }else {
            lblCityName.setText("Please wait...");
            lblTemp.setText("Getting weather information.");
            lblDescrpition.setText("");
            imgWeather.setImageDrawable(null);
            chosenCity = chosenCity.replaceAll(" ", "");
            URL url = null;
            try {
                url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + chosenCity + ",uk&units=metric");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            new ReadWeatherInfoTask().execute(url);
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
