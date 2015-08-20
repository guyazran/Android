package com.example.guyazran.mysmsapp;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lstSentMessages;

    EditText txtMessage;
    EditText txtRecipient;
    TextView lblCheckSent;

    ArrayList<MySMS> mySMSList;
    MySMSArrayAdapter adapter;

    public static final String SMS_SENT = "SMS_SENT";
    public static final String SMS_DELIVERED = "SMS_DELIVERED";
    PendingIntent sendPendingIntent, deliveredPendingIntent;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMessage = (EditText)findViewById(R.id.txtMessage);
        txtRecipient = (EditText)findViewById(R.id.txtRecipient);

        mySMSList = new ArrayList<MySMS>();

        lstSentMessages = (ListView)findViewById(R.id.lstSentMessages);
        adapter = new MySMSArrayAdapter(this, R.layout.item_message, R.id.lblMessage, R.id.lblRecipient, R.id.lblCheckSent,mySMSList);
        lstSentMessages.setAdapter(adapter);

        sendPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
        deliveredPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

    }

    @Override
    protected void onResume() {
        super.onResume();

        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()){
                    case RESULT_OK:
                        mySMSList.get(lstSentMessages.getCount() - 1).setIsSent(true);
                        adapter.notifyDataSetChanged();
                        break;
                    case RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS Not Sent", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case RESULT_OK:
                        mySMSList.get(lstSentMessages.getCount() - 1).setIsDelivered(true);
                        adapter.notifyDataSetChanged();
                        break;
                    case RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS Not Delivered", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        registerReceiver(smsSentReceiver, new IntentFilter(SMS_SENT));
        registerReceiver(smsDeliveredReceiver, new IntentFilter(SMS_DELIVERED));
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

    public void sendMessage(View view) {
        String message = txtMessage.getText().toString();
        String recipient = txtRecipient.getText().toString();
        Boolean isRecipientNumber;

        if (!message.equals("") && !recipient.equals("")) {
            try{
                Integer.valueOf(recipient);
                isRecipientNumber = true;
            }catch (Exception ex){
                isRecipientNumber = false;
            }
            if (isRecipientNumber) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(recipient, null, message, sendPendingIntent, deliveredPendingIntent);
            }
            mySMSList.add(new MySMS(message, recipient));
            adapter.notifyDataSetChanged();
            lstSentMessages.setSelection(lstSentMessages.getCount() - 1);
        }

    }
}
