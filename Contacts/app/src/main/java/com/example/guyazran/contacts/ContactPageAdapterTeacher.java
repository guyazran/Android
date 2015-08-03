package com.example.guyazran.contacts;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by guyazran on 8/3/15.
 */
public class ContactPageAdapterTeacher extends PagerAdapter {

    Contact[] contacts;

    ContactPageAdapterTeacher(Contact[] contacts){
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.page_contact_teacher, null);
        TextView lblFirstName = (TextView)view.findViewById(R.id.lblFirstNameTeacher);
        TextView lblLastName = (TextView)view.findViewById(R.id.lblLastNameTeacher);
        ImageView imgContactPicture = (ImageView)view.findViewById(R.id.imgPersonPictureTeacher);
        Button btnEditTeacher = (Button)view.findViewById(R.id.btnEditTeacher);
        btnEditTeacher.setOnClickListener(new EditButtonListener(lblFirstName, lblLastName, contacts[position], container.getContext()));
        //btnEditTeacher.setTag(contacts[position]);
        lblFirstName.setText(contacts[position].getFirstName());
        lblLastName.setText(contacts[position].getLastName());
        imgContactPicture.setImageResource(contacts[position].getImage());
        container.addView(view, 0);
        return view;
    }

    class EditContactListener implements EditContactDialogFragmentTeacher.EditContactDialogListenerTeacher{

        TextView lblFirstName, lblLastName;
        Contact contactToEdit;

        public EditContactListener(TextView lblFirstName, TextView lblLastName, Contact contactToEdit) {
            this.lblFirstName = lblFirstName;
            this.lblLastName = lblLastName;
            this.contactToEdit = contactToEdit;
        }

        @Override
        public void onEditComplete(String newFirstName, String newLastname) {
            if (lblFirstName != null)
                lblFirstName.setText(newFirstName);
            if (lblLastName != null)
                lblLastName.setText(newLastname);
            if (contactToEdit != null){
                contactToEdit.setFirstName(newFirstName);
                contactToEdit.setLastName(newLastname);
            }
        }
    }

    class EditButtonListener implements View.OnClickListener{

        TextView lblFirstnameofContact, lblLastNameOfContact;
        Contact clickedContacct;
        Context context;

        public EditButtonListener(TextView lblFirstnameofContact, TextView lblLastNameOfContact, Contact clickedContacct, Context context){
            this.lblFirstnameofContact = lblFirstnameofContact;
            this.lblLastNameOfContact = lblLastNameOfContact;
            this.clickedContacct = clickedContacct;
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(context, clickedContacct.toString(), Toast.LENGTH_LONG).show();
            //clickedContacct.setFirstName(clickedContacct.getFirstName() + "!");
            //lblFirstnameofContact.setText(clickedContacct.getFirstName());
            if (context instanceof AppCompatActivity) {
                FragmentManager fragmentManager = ((AppCompatActivity) context).getFragmentManager();
                EditContactDialogFragmentTeacher editContactDialogFragmentTeacher = new EditContactDialogFragmentTeacher();
                editContactDialogFragmentTeacher.setFragment("Edit Contact",
                        new EditContactListener(lblFirstnameofContact, lblLastNameOfContact, clickedContacct),
                        clickedContacct.getFirstName(), clickedContacct.getLastName());
                editContactDialogFragmentTeacher.show(fragmentManager, "edit contact");
            }
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
