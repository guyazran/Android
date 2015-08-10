package com.example.guyazran.learninglistview2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by guyazran on 8/10/15.
 */
public class ItemArrayAdapter extends ArrayAdapter<ListItem> {

    private ListItem[] listItems;
    private Activity activity;
    private int resource, textViewResourceId, subTexViewResourceId, imageViewResourceId;

    public ItemArrayAdapter(Activity context, int resource, int textViewResourceId, int subTexViewResourceId,
                              int imageViewResourceId, ListItem[] listItems) {
        super(context, resource, textViewResourceId, listItems);

        this.activity = context;
        this.resource = resource;
        this.listItems = listItems;
        this.textViewResourceId = textViewResourceId;
        this.subTexViewResourceId = subTexViewResourceId;
        this.imageViewResourceId = imageViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(resource, null, true);

        TextView txtItem = (TextView)rowView.findViewById(textViewResourceId);
        TextView txtSubItem = (TextView)rowView.findViewById(subTexViewResourceId);
        ImageView imgItem = (ImageView)rowView.findViewById(imageViewResourceId);

        if (listItems != null && listItems[position] != null){
            if (listItems[position].getItem() != null)
                txtItem.setText(listItems[position].getItem());
            if (listItems[position].getSubItem() != null)
                txtSubItem.setText(listItems[position].getSubItem());
            if (listItems[position].getImage() != 0)
                imgItem.setImageResource(listItems[position].getImage());
        }


        return rowView;
    }
}
