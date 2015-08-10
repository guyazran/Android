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
public class AdvancedArrayAdapter extends ArrayAdapter<ListItem> {

    private ListItem[] listItems;
    private Activity activity;
    private int resource, textViewResourceId, subTexViewResourceId, imageViewResourceId;

    public AdvancedArrayAdapter(Activity context, int resource, int textViewResourceId, int subTexViewResourceId,
                                int imageViewResourceId, ListItem[] listItems) {
        super(context, resource, textViewResourceId, listItems);

        this.activity = context;
        this.resource = resource;
        this.listItems = listItems;
        this.textViewResourceId = textViewResourceId;
        this.subTexViewResourceId = subTexViewResourceId;
        this.imageViewResourceId = imageViewResourceId;
    }

    static class ViewContainer{
        TextView txtItem, txtSubItem;
        ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewContainer viewContainer;
        View rowView = convertView;

        if (rowView == null) {
            Log.d("Guy", "AdvancedArrayAdapter - new");
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(resource, null, true);
            viewContainer = new ViewContainer();
            viewContainer.txtItem = (TextView) rowView.findViewById(textViewResourceId);
            viewContainer.txtSubItem = (TextView) rowView.findViewById(subTexViewResourceId);
            viewContainer.imageView = (ImageView) rowView.findViewById(imageViewResourceId);
            rowView.setTag(viewContainer);
        } else {
            Log.d("Guy", "AdvancedArrayAdapter - recycling");
            viewContainer = (ViewContainer) rowView.getTag();
        }
        if (listItems != null && listItems[position] != null) {
            if (listItems[position].getItem() != null)
                viewContainer.txtItem.setText(listItems[position].getItem());
            if (listItems[position].getSubItem() != null)
                viewContainer.txtSubItem.setText(listItems[position].getSubItem());
            if (listItems[position].getImage() != 0)
                viewContainer.imageView.setImageResource(listItems[position].getImage());
        }
        return rowView;
    }
}
