package com.example.guyazran.usingcheckboxes;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    Toast t;

    //for teacher's solution
    LinearLayout rating;
    CheckBox star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckBox chkAutosave = (CheckBox) findViewById(R.id.chkAutosave);
        chkAutosave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (t != null)
                    t.cancel();

                String tag = (String) buttonView.getTag();

                t = Toast.makeText(getBaseContext(), "chkAutosave is " + (isChecked ? "checked" : "unchecked") + " " + tag, Toast.LENGTH_LONG);
                t.show();
            }
        });
/*
        //my solution
        CompoundButton.OnClickListener rateListener = new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ratingSystem = (LinearLayout) findViewById(R.id.ratingSystem);
                for (Integer i = 1; i<= ratingSystem.getChildCount(); i++){
                    CheckBox chkStar = (CheckBox) ratingSystem.findViewWithTag(i.toString());
                    chkStar.setChecked(false);
                }
                CheckBox chkRating = (CheckBox) v;
                for (Integer i = 1; i <= Integer.valueOf((String) chkRating.getTag()); i++) {
                    CheckBox chkStar = (CheckBox) ratingSystem.findViewWithTag(i.toString());
                    chkStar.setChecked(true);
                }
            }
        };

        CheckBox chkStar1 = (CheckBox) findViewById(R.id.chkStar1);
        chkStar1.setOnClickListener(rateListener);
        CheckBox chkStar2 = (CheckBox) findViewById(R.id.chkStar2);
        chkStar2.setOnClickListener(rateListener);
        CheckBox chkStar3 = (CheckBox) findViewById(R.id.chkStar3);
        chkStar3.setOnClickListener(rateListener);
        CheckBox chkStar4 = (CheckBox) findViewById(R.id.chkStar4);
        chkStar4.setOnClickListener(rateListener);
        CheckBox chkStar5 = (CheckBox) findViewById(R.id.chkStar5);
        chkStar5.setOnClickListener(rateListener);
*/

        //teacher's solution - more optimized
        rating = (LinearLayout)findViewById(R.id.ratingSystem);

        for (int i = 1; i <= 5; i++) {
            star = (CheckBox) rating.findViewWithTag(String.valueOf(i));
            star.setOnClickListener(startListener);
        }

    }

    private View.OnClickListener startListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = Integer.valueOf((String) v.getTag());
            for (int i = 1; i <= tag; i++) {
                star = (CheckBox) rating.findViewWithTag(String.valueOf(i));
                star.setChecked(true);
            }
            for (int i = tag + 1; i <= 5; i++) {
                star = (CheckBox) rating.findViewWithTag(String.valueOf(i));
                star.setChecked(false);
            }
        }
    };

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
