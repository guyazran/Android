package com.example.guyazran.callingotherapps;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        //calling map app
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //URI - Uniform Resource Identifier (URL - Uniform Resource Locator is a type of URI)
        Uri uri = Uri.parse("geo: 32.084667, 34.800966");
        intent.setData(uri);
        startActivity(intent);
        */

        /*
        //calling Google Play to download Angry Birds
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=com.rovio.angrybirds"));
        startActivity(intent);
        */

        /*
        //send email
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        String[] to = {"abc@gmail.com", "dfg@gmail.com"};
        String[] cc = {"rty@gmail.com", "zxc@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_CC, cc);
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject...");
        intent.putExtra(Intent.EXTRA_TEXT, "message bla bla...");
        //intent.setType("message/rfc822");
        intent.setType("text/plain");
        startActivity(intent);
        */

        //attach image
        Uri uriToImage = Uri.parse("android.resource://com.example.guyazran.callingotherapps/drawable/"
                + Integer.toString(R.drawable.angry_birds));
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, uriToImage);
        intent.putExtra(Intent.EXTRA_TEXT, "text to send with image");
        startActivity(intent);


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
