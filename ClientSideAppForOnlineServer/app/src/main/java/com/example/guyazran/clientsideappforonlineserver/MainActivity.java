package com.example.guyazran.clientsideappforonlineserver;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    public static final String SERVER_IP = "130.211.51.153";
    public static final int PORT = 3000;

    public static final int SIGN_UP = 1;
    public static final int LOGIN = 2;
    public static final int LOGOUT = 3;

    EditText txtUsername;
    View loggedInView;
    TextView lblLoggedIn;

    Button btnLogIn;
    Button btnSignUp;
    Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        loggedInView = findViewById(R.id.loggedInView);
        lblLoggedIn = (TextView) findViewById(R.id.lblLoggedIn);

        btnLogIn = (Button) findViewById(R.id.btnLogIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
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
        blockUI();
        contactServer(LOGIN);
    }

    public void btnSignUp(View view) {
        blockUI();
        contactServer(SIGN_UP);
    }

    public void btnLogOut(View view) {
        contactServer(LOGOUT);
    }

    public void contactServer(int action){
        String username = txtUsername.getText().toString();

        class ContactServerTask extends AsyncTask<Integer, Void, String>{

            String username;

            public ContactServerTask(String username){
                this.username = username;
            }

            @Override
            protected String doInBackground(Integer... params) {
                int action = params[0];

                Socket clientSocket = null;
                InputStream inputStream = null;
                OutputStream outputStream = null;

                String response = "Action Failed";

                try {
                    clientSocket = new Socket(SERVER_IP, PORT);

                    //send username and password
                    outputStream = clientSocket.getOutputStream();
                    outputStream.write(action);
                    byte[] usernameBytes = username.getBytes();
                    outputStream.write(usernameBytes.length);
                    outputStream.write(usernameBytes);

                    //receive response
                    inputStream = clientSocket.getInputStream();
                    int responseLength = inputStream.read();
                    byte[] responseBytes = new byte[responseLength];
                    int actuallyRead = inputStream.read(responseBytes);
                    if (actuallyRead != -1){
                        response = new String(responseBytes);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (clientSocket != null){
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
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
                }

                return response;
            }

            @Override
            protected void onPostExecute(String s) {
                releaseUI();
                Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();

                switch (s){
                    case "Login Successful":
                        loggedInView.setBackgroundColor(Color.GREEN);
                        lblLoggedIn.setText("Logged In");
                        blockLogInSignUp();
                        break;
                    case "Logout Successful":
                        loggedInView.setBackgroundColor(Color.parseColor("#CCCCCC"));
                        lblLoggedIn.setText("Logged Out");
                        blockLogOut();
                        break;
                    default:
                        blockLogOut();
                        break;
                }
            }
        }

        new ContactServerTask(username).execute(action);
    }

    private void blockUI(){
        txtUsername.setEnabled(false);
        btnLogIn.setEnabled(false);
        btnSignUp.setEnabled(false);
        btnLogOut.setEnabled(false);
    }

    private void blockLogInSignUp(){
        btnLogIn.setEnabled(false);
        btnSignUp.setEnabled(false);
    }

    private void blockLogOut(){
        btnLogOut.setEnabled(false);
    }

    private void releaseUI(){
        txtUsername.setEnabled(true);
        btnLogIn.setEnabled(true);
        btnSignUp.setEnabled(true);
        btnLogOut.setEnabled(true);
    }
}
