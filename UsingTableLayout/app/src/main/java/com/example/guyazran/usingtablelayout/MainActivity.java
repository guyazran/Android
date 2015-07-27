package com.example.guyazran.usingtablelayout;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashSet;


public class MainActivity extends ActionBarActivity implements LoginThread.LoginListener{

    Button btnSignIn;
    HashSet<User> users = new HashSet<User>();
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        users.add(new User("guy", "123"));
        users.add(new User("gil", "321"));
        users.add(new User("leon", "456"));
        users.add(new User("ariel", "654"));
        users.add(new User("lev", "789"));
        users.add(new User("luda", "987"));
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

    public void btnSignIn(View view) {

        btnSignIn.setText("please wait...");
        btnSignIn.setEnabled(false);
        EditText txtUsername = (EditText)findViewById(R.id.txtUsername);
        EditText txtPassword = (EditText)findViewById(R.id.txtPassword);
        final String username = txtUsername.getText().toString();
        final String password = txtPassword.getText().toString();

        if (username.length() == 0 || password.length() == 0) {
            Toast.makeText(getBaseContext(), "Username and password are required", Toast.LENGTH_LONG).show();
            btnSignIn.setText("Sign In");
            return;
        }

        LoginThread loginThread = new LoginThread(username, password,users, this);
        loginThread.start();

    }

    @Override
    public void invalidUserOrPassword() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
                btnSignIn.setText("Sign In");
                btnSignIn.setEnabled(true);
            }
        });
    }

    @Override
    public void loginSuccessful() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), "Login Successful", Toast.LENGTH_LONG).show();
                btnSignIn.setText("Sign In");
                btnSignIn.setEnabled(true);
                Intent intent = new Intent(getBaseContext(), SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
