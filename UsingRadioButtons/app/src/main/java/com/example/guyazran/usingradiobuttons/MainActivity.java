package com.example.guyazran.usingradiobuttons;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup radioGroup1 = (RadioGroup)findViewById(R.id.radioButtonGroup1);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String s = "";
                /*
                switch (checkedId){
                    case R.id.rdb1:
                        s = "Option 1";
                        break;
                    case R.id.rdb2:
                        s = "Option 2";
                        break;
                    default:
                        s = "unknown";
                        break;
                }
                Toast.makeText(getBaseContext(), s + " is selected", Toast.LENGTH_LONG).show();
                */
                int howManyRadioButtons = group.getChildCount();
                for (int i = 0; i < howManyRadioButtons; i++) {
                    RadioButton radioButton = (RadioButton) group.getChildAt(i);
                    if (radioButton.isChecked()){
                        s = radioButton.getText().toString();
                        break;
                    }
                }
                Toast.makeText(getBaseContext(), s + " is selected", Toast.LENGTH_LONG).show();
            }
        });
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
