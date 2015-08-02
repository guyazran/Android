package com.example.guyazran.contacts;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by guyazran on 8/2/15.
 */
public class ContactsPageAdapter extends PagerAdapter {

    ImageView image;
    TextView firstName;
    TextView lastName;

    private Contact[] contacts;

    public ContactsPageAdapter(Contact[] contacts){
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.page_contact, null);

        image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(contacts[position].image);
        firstName = (TextView)view.findViewById(R.id.firstName);
        firstName.setText(contacts[position].firstName);
        lastName = (TextView)view.findViewById(R.id.lastName);
        lastName.setText(contacts[position].lastName);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public void setContactInfo(String firstName, String lastName){
        this.firstName.setText(firstName);
        this.lastName.setText(lastName);
    }
}
