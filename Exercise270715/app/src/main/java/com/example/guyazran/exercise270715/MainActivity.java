package com.example.guyazran.exercise270715;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements SumThread.SumListener{

    TextView lblResult;
    Button btnPlus;
    Handler handler = new Handler();
    EditText txtNum1;
    EditText txtNum2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblResult = (TextView)findViewById(R.id.lblResult);
        btnPlus = (Button)findViewById(R.id.btnPlus);
        txtNum1 = (EditText)findViewById(R.id.txtNum1);
        txtNum2 = (EditText)findViewById(R.id.txtNum2);
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

    public void btnPlus(View view) {

        final String num1String = txtNum1.getText().toString();
        final String num2String = txtNum2.getText().toString();
        if (num1String .equals("") || num2String.equals("")) {
            Toast.makeText(this, "Please enter a digit in both fields", Toast.LENGTH_LONG).show();
            return;
        }

        btnPlus.setText("Calculating");
        btnPlus.setEnabled(false);
        lblResult.setText(" please wait...");

        int num1 = Integer.valueOf(num1String);
        int num2 = Integer.valueOf(num2String);

        SumThread sumThread = new SumThread(num1, num2, this);
        sumThread.start();

    }

    @Override
    public void sum(int result) {

        class UpdateGuiWithResult implements Runnable{
            int result;
            public UpdateGuiWithResult(int result){
                this.result = result;
            }

            @Override
            public void run() {
                btnPlus.setText("+");
                btnPlus.setEnabled(true);
                lblResult.setText(" The Result is: " + result);
            }
        }

        handler.post(new UpdateGuiWithResult(result));
    }
}
