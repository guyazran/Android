package com.example.guyazran.currencyconverter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    Spinner spnFromCurrency;
    Spinner spnToCurrency;
    TextView lblResult;
    EditText txtFromAmount;


    String[] currencies = {
            "AFA-Afghanistan Afghani",
            "ALL-Albanian Lek",
            "DZD-Algerian Dinar",
            "ARS-Argentine Peso",
            "AWG-Aruba Florin",
            "AUD-Australian Dollar",
            "BSD-Bahamian Dollar",
            "BHD-Bahraini Dinar",
            "BDT-Bangladesh Taka",
            "BBD-Barbados Dollar",
            "BZD-Belize Dollar",
            "BMD-Bermuda Dollar",
            "BTN-Bhutan Ngultrum",
            "BOB-Bolivian Boliviano",
            "BWP-Botswana Pula",
            "BRL-Brazilian Real",
            "GBP-British Pound",
            "BND-Brunei Dollar",
            "BIF-Burundi Franc",
            "XOF-CFA Franc (BCEAO)",
            "XAF-CFA Franc (BEAC)",
            "KHR-Cambodia Riel",
            "CAD-Canadian Dollar",
            "CVE-Cape Verde Escudo",
            "KYD-Cayman Islands Dollar",
            "CLP-Chilean Peso",
            "CNY-Chinese Yuan",
            "COP-Colombian Peso",
            "KMF-Comoros Franc",
            "CRC-Costa Rica Colon",
            "HRK-Croatian Kuna",
            "CUP-Cuban Peso",
            "CYP-Cyprus Pound",
            "CZK-Czech Koruna",
            "DKK-Danish Krone",
            "DJF-Dijibouti Franc",
            "DOP-Dominican Peso",
            "XCD-East Caribbean Dollar",
            "EGP-Egyptian Pound",
            "SVC-El Salvador Colon",
            "EEK-Estonian Kroon",
            "ETB-Ethiopian Birr",
            "EUR-Euro",
            "FKP-Falkland Islands Pound",
            "GMD-Gambian Dalasi",
            "GHC-Ghanian Cedi",
            "GIP-Gibraltar Pound",
            "XAU-Gold Ounces",
            "GTQ-Guatemala Quetzal",
            "GNF-Guinea Franc",
            "GYD-Guyana Dollar",
            "HTG-Haiti Gourde",
            "HNL-Honduras Lempira",
            "HKD-Hong Kong Dollar",
            "HUF-Hungarian Forint",
            "ISK-Iceland Krona",
            "INR-Indian Rupee",
            "IDR-Indonesian Rupiah",
            "IQD-Iraqi Dinar",
            "ILS-Israeli Shekel",
            "JMD-Jamaican Dollar",
            "JPY-Japanese Yen",
            "JOD-Jordanian Dinar",
            "KZT-Kazakhstan Tenge",
            "KES-Kenyan Shilling",
            "KRW-Korean Won",
            "KWD-Kuwaiti Dinar",
            "LAK-Lao Kip",
            "LVL-Latvian Lat",
            "LBP-Lebanese Pound",
            "LSL-Lesotho Loti",
            "LRD-Liberian Dollar",
            "LYD-Libyan Dinar",
            "LTL-Lithuanian Lita",
            "MOP-Macau Pataca",
            "MKD-Macedonian Denar",
            "MGF-Malagasy Franc",
            "MWK-Malawi Kwacha",
            "MYR-Malaysian Ringgit",
            "MVR-Maldives Rufiyaa",
            "MTL-Maltese Lira",
            "MRO-Mauritania Ougulya",
            "MUR-Mauritius Rupee",
            "MXN-Mexican Peso",
            "MDL-Moldovan Leu",
            "MNT-Mongolian Tugrik",
            "MAD-Moroccan Dirham",
            "MZM-Mozambique Metical",
            "MMK-Myanmar Kyat",
            "NAD-Namibian Dollar",
            "NPR-Nepalese Rupee",
            "ANG-Neth Antilles Guilder",
            "NZD-New Zealand Dollar",
            "NIO-Nicaragua Cordoba",
            "NGN-Nigerian Naira",
            "KPW-North Korean Won",
            "NOK-Norwegian Krone",
            "OMR-Omani Rial",
            "XPF-Pacific Franc",
            "PKR-Pakistani Rupee",
            "XPD-Palladium Ounces",
            "PAB-Panama Balboa",
            "PGK-Papua New Guinea Kina",
            "PYG-Paraguayan Guarani",
            "PEN-Peruvian Nuevo Sol",
            "PHP-Philippine Peso",
            "XPT-Platinum Ounces",
            "PLN-Polish Zloty",
            "QAR-Qatar Rial",
            "ROL-Romanian Leu",
            "RUB-Russian Rouble",
            "WST-Samoa Tala",
            "STD-Sao Tome Dobra",
            "SAR-Saudi Arabian Riyal",
            "SCR-Seychelles Rupee",
            "SLL-Sierra Leone Leone",
            "XAG-Silver Ounces",
            "SGD-Singapore Dollar",
            "SKK-Slovak Koruna",
            "SIT-Slovenian Tolar",
            "SBD-Solomon Islands Dollar",
            "SOS-Somali Shilling",
            "ZAR-South African Rand",
            "LKR-Sri Lanka Rupee",
            "SHP-St Helena Pound",
            "SDD-Sudanese Dinar",
            "SRG-Surinam Guilder",
            "SZL-Swaziland Lilageni",
            "SEK-Swedish Krona",
            "TRY-Turkey Lira",
            "CHF-Swiss Franc",
            "SYP-Syrian Pound",
            "TWD-Taiwan Dollar",
            "TZS-Tanzanian Shilling",
            "THB-Thai Baht",
            "TOP-Tonga Pa'anga",
            "TTD-Trinidad&amp;Tobago Dollar",
            "TND-Tunisian Dinar",
            "TRL-Turkish Lira",
            "USD-U.S. Dollar",
            "AED-UAE Dirham",
            "UGX-Ugandan Shilling",
            "UAH-Ukraine Hryvnia",
            "UYU-Uruguayan New Peso",
            "VUV-Vanuatu Vatu",
            "VEB-Venezuelan Bolivar",
            "VND-Vietnam Dong",
            "YER-Yemen Riyal",
            "YUM-Yugoslav Dinar",
            "ZMK-Zambian Kwacha",
            "ZWD-Zimbabwe Dollar"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnFromCurrency = (Spinner)findViewById(R.id.spnFromCurrency);
        spnToCurrency = (Spinner)findViewById(R.id.spnToCurrency);
        lblResult = (TextView)findViewById(R.id.lblResult);
        txtFromAmount = (EditText)findViewById(R.id.txtFromAmount);

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        spnFromCurrency.setAdapter(currencyAdapter);
        spnToCurrency.setAdapter(currencyAdapter);
    }

    public static InputStream openHttpGetConnection(String url) throws Exception{
        InputStream inputStream = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
        inputStream = httpResponse.getEntity().getContent();
        return inputStream;
    }

    public static InputStream openHttpPostConnection(String url, String from, String to) throws Exception{
        InputStream inputStream = null;
        HttpClient httpClient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Host", "www.webservicex.net");
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("FromCurrency", from));
        nameValuePairs.add(new BasicNameValuePair("ToCurrency", to));

        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse httpResponse = httpClient.execute(httpPost);
        inputStream = httpResponse.getEntity().getContent();
        return inputStream;
    }

    public String downloadText(String url){
        int BUFFER_SIZE = 1024;
        InputStream inputStream = null;
        String from = getCurrencyInit(currencies[spnFromCurrency.getSelectedItemPosition()]);
        String to = getCurrencyInit(currencies[spnToCurrency.getSelectedItemPosition()]);
        try {
            //inputStream = openHttpGetConnection(url);
            inputStream = openHttpPostConnection(url, from, to);
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

    private class DownloadTextTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return downloadText(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            float rate = convertRateToFloat(s);
            String fromAmount = txtFromAmount.getText().toString();
            float amount = 1f;
            if (!fromAmount.equals("")){
                amount *= Float.valueOf(fromAmount);
            }
            float conversion = rate * amount;
            lblResult.setText(conversion + "");
        }
    }

    public void btnConvert(View view) {
        lblResult.setText("Please wait...");

//        String from = getCurrencyInit(currencies[spnFromCurrency.getSelectedItemPosition()]);
//        String to = getCurrencyInit(currencies[spnToCurrency.getSelectedItemPosition()]);
//
//
//        String url = "http://www.webservicex.net/currencyconvertor.asmx/ConversionRate?FromCurrency=" + from + "&ToCurrency=" + to;
        String url = "http://www.webservicex.net/currencyconvertor.asmx/ConversionRate";
        new DownloadTextTask().execute(url);
    }

    public String getCurrencyInit(String s){
        s = s.substring(0, s.indexOf('-'));
        return s;
    }

    private static float convertRateToFloat(String s){
//        int positionOfFirstDigit = s.indexOf('>', s.indexOf('>')+1)+1;
//        String rateAsString = s.substring(positionOfFirstDigit, s.indexOf('<', positionOfFirstDigit));

        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(s));
            doc = builder.parse(inputSource);
        }catch(Exception ex){
            Log.d("Guy", "error: " + ex.getMessage());
        }

        doc.getDocumentElement().normalize();

        String rateAsString = doc.getDocumentElement().getTextContent();

        float rate = Float.valueOf(rateAsString);

        return rate;
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
