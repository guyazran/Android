package com.example.guyazran.learninglistview2;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by guyazran on 8/10/15.
 */
public class CustomArrayAdapter extends ArrayAdapter<String> {

    private String[] items, subItems;
    private Activity activity;
    int[] images;
    private int resource, textViewResourceId, subTexViewResourceId, imageViewResourceId;

    public CustomArrayAdapter(Activity context, int resource, int textViewResourceId, int subTexViewResourceId,
                              int imageViewResourceId, String[] items, String[] subItems, int[] images) {
        super(context, resource, textViewResourceId, items);
        this.subItems = subItems;
        this.images = images;
        this.activity = context;
        this.resource = resource;
        this.items = items;
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

        if (items != null && position<items.length)
            txtItem.setText(items[position]);
        if (subItems != null && position<subItems.length)
            txtSubItem.setText(subItems[position]);
        if (images != null && position<images.length)
            imgItem.setImageResource(images[position]);

        Log.d("Guy", String.valueOf(position));
        return rowView;
    }
}
