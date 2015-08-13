package com.example.guyazran.learninggridview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by guyazran on 8/13/15.
 */
public class ImageAdapter extends BaseAdapter {

    private Context context;
    private Integer[] imageIDs;

    public ImageAdapter(Context context, Integer[] imageIDs) {
        this.context = context;
        this.imageIDs = imageIDs;
    }

    @Override
    public int getCount() {
        return imageIDs.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        if (convertView == null){
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5,5,5,5);
        } else {
            imageView = (ImageView)convertView;
        }
        imageView.setImageResource(imageIDs[position]);

        return imageView;
    }
}
