package com.example.guyazran.whatsappclient;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MessagesActivity extends AppCompatActivity implements CheckForMessagesThread.NewMessagesListener {

    ListView lstMessages;
    EditText txtMessage, txtRecipient;
    String userName, password;
    CheckForMessagesThread checkForMessagesThread;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        lstMessages = (ListView) findViewById(R.id.lstMessages);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
        txtRecipient = (EditText) findViewById(R.id.txtRecipient);

        userName = getIntent().getStringExtra("userName");
        password = getIntent().getStringExtra("password");



    }

    @Override
    protected void onResume() {
        super.onResume();

        checkForMessagesThread = new CheckForMessagesThread(userName, password, this);
        checkForMessagesThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        checkForMessagesThread.stopRunning();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_messages, menu);
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

    public void btnSend(View view) {
        String messageContent = txtMessage.getText().toString();
        String recipient = txtRecipient.getText().toString();

        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                String messageContent = params[0];
                String recipient = params[1];
                boolean valid = false;
                Socket clientSocket = null;
                InputStream inputStream = null;
                OutputStream outputStream = null;

                try {
                    clientSocket = new Socket(MainActivity.SERVER_IP, MainActivity.PORT);
                    //send action (send message)
                    inputStream = clientSocket.getInputStream();
                    outputStream = clientSocket.getOutputStream();
                    outputStream.write(MainActivity.SEND_MESSAGE);
                    //send username
                    byte[] userNameBytes = userName.getBytes();
                    outputStream.write(userNameBytes.length);
                    outputStream.write(userNameBytes);
                    //send password
                    byte[] passwordBytes = password.getBytes();
                    outputStream.write(passwordBytes.length);
                    outputStream.write(passwordBytes);
                    //send message
                    byte[] messagesBytes = messageContent.getBytes();
                    outputStream.write(messagesBytes.length);
                    outputStream.write(messagesBytes);
                    //send recipient
                    byte[] recipientBytes = recipient.getBytes();
                    outputStream.write(recipientBytes.length);
                    outputStream.write(recipientBytes);

                    int response = inputStream.read();
                    valid = response == MainActivity.SUCCESS;

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
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
                if (valid) {
                    Toast.makeText(getBaseContext(), "Message Sent", Toast.LENGTH_LONG).show();
                    //TODO: add to listview of messages
                } else {
                    Toast.makeText(getBaseContext(), "Message Not Sent", Toast.LENGTH_LONG).show();
                }
            }
        }.execute(messageContent, recipient);
    }

    @Override
    public void onNewMessages(Message[] newMessages) {
        class HandleNewMessagesRunable implements Runnable{

            Message[] newMessages;

            public HandleNewMessagesRunable(Message[] newMessages){
                this.newMessages = newMessages;
            }

            @Override
            public void run() {
                for (Message msg : newMessages){
                    Toast.makeText(getBaseContext(), msg.getContent() + " from: " + msg.getSender(), Toast.LENGTH_LONG).show();
                }
            }
        }
        handler.post(new HandleNewMessagesRunable(newMessages));
    }
}