package com.example.guyazran.mysmsapp;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by guyazran on 8/19/15.
 */
public class MySMSArrayAdapter extends ArrayAdapter<MySMS> {
    private ArrayList<MySMS> mySMSList;
    private Activity activity;
    private int resource, messageResourceId, recipientResourceId, checkSentResourceId;
    private final static String SMS_SENT = "SMS_SENT";
    private final static String SMS_DELIVERED = "SMS_DELIVERED";

    public MySMSArrayAdapter(Activity activity, int resource, int messageResourceId, int recipientResourceId,
                             int checkSentResourceId, ArrayList<MySMS> mySMSList) {
        super(activity, resource, mySMSList);

        this.mySMSList = mySMSList;
        this.activity = activity;
        this.resource = resource;
        this.messageResourceId = messageResourceId;
        this.recipientResourceId = recipientResourceId;
        this.checkSentResourceId = checkSentResourceId;
    }

    static class ViewContainer{
        TextView lblMessage, lblRecipient, lblCheckSent;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewContainer viewContainer;
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(resource, null, true);
            viewContainer = new ViewContainer();
            viewContainer.lblRecipient = (TextView) rowView.findViewById(recipientResourceId);
            viewContainer.lblMessage = (TextView) rowView.findViewById(messageResourceId);
            viewContainer.lblCheckSent = (TextView) rowView.findViewById(R.id.lblCheckSent);
            rowView.setTag(viewContainer);
        } else {
            viewContainer = (ViewContainer) rowView.getTag();
        }
        if (mySMSList != null && mySMSList.get(position) != null) {
            if (mySMSList.get(position).getMessage() != null)
                viewContainer.lblMessage.setText(mySMSList.get(position).getMessage());
            if (mySMSList.get(position).getRecipient() != null)
                viewContainer.lblRecipient.setText("To: " + mySMSList.get(position).getRecipient());
            viewContainer.lblCheckSent.setText("");
            viewContainer.lblCheckSent.setTextColor(Color.BLACK);
        }

        return rowView;
    }

}
