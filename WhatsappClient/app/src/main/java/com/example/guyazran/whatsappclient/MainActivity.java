package com.example.guyazran.whatsappclient;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    public static final String SERVER_IP = "10.0.2.2";
    public static final int SIGN_UP = 10;
    public static final int LOG_IN = 20;
    public static final int SEND_MESSAGE = 30;
    public static final int CHECK_FOR_MESSAGES = 40;
    public static final int FAILURE = 50;
    public static final int SUCCESS = 60;
    public static final int PORT = 3000;

    EditText txtUserName, txtPassword;
    TextView txtStatus;

    Button btnSignUp, btnLogIn;

    String userName;
    String password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUserName = (EditText)findViewById(R.id.txtUserName);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtStatus = (TextView)findViewById(R.id.txtStatus);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        btnLogIn = (Button)findViewById(R.id.btnLogIn);
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

    public void btnLogIn(View view) {
        contactServer(LOG_IN);
    }

    public void btnSignUp(View view) {
        contactServer(SIGN_UP);
    }

    private void blockUI(){
        txtStatus.setText("Please wait...");
        txtUserName.setEnabled(false);
        txtPassword.setEnabled(false);
        btnLogIn.setEnabled(false);
        btnSignUp.setEnabled(false);
    }

    private void releaseUI(){
        txtUserName.setEnabled(true);
        txtPassword.setEnabled(true);
        btnLogIn.setEnabled(true);
        btnSignUp.setEnabled(true);
    }

    void contactServer(int action){
        userName = txtUserName.getText().toString();
        password = txtPassword.getText().toString();
        blockUI();

        new AsyncTask<Integer, Void, Boolean>(){

            @Override
            protected Boolean doInBackground(Integer... params) {
                int action = params[0];
                boolean valid = false;
                Socket clientSocket = null;
                InputStream inputStream = null;
                OutputStream outputStream = null;
                try {
                    clientSocket = new Socket(SERVER_IP, PORT);
                    inputStream = clientSocket.getInputStream();
                    outputStream = clientSocket.getOutputStream();
                    outputStream.write(action);
                    byte[] userNameBytes = userName.getBytes();
                    outputStream.write(userNameBytes.length);
                    outputStream.write(userNameBytes);
                    byte[] passwordBytes = password.getBytes();
                    outputStream.write(passwordBytes.length);
                    outputStream.write(passwordBytes);
                    int response = inputStream.read();
                    valid = response == SUCCESS;

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (inputStream != null){
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (outputStream != null){
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (clientSocket != null){
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                return valid;
            }

            @Override
            protected void onPostExecute(Boolean valid) {
                releaseUI();
                if (valid){
                    txtStatus.setText("okay...");
                    txtStatus.setTextColor(Color.GREEN);
                    Intent intent = new Intent(getBaseContext(), MessagesActivity.class);
                    intent.putExtra("userName", userName);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }else {
                    //TODO: respond to specific action and different errors
                    txtStatus.setText("Username or password are invalid");
                    txtStatus.setTextColor(Color.RED);
                }
            }
        }.execute(action);


    }
}
