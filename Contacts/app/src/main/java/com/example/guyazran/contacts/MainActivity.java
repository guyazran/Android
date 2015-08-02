package com.example.guyazran.contacts;

import android.app.FragmentManager;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements EditContactDialogFragment.EditContactDialogListener {

    ViewPager viewPager;
    Contact[] contacts;

    TextView shownFirstName;
    TextView shownLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Contact jon = new Contact("Jon", "Smith", R.drawable.person1);
        Contact adam = new Contact("Adam", "Jones", R.drawable.person2);
        Contact david = new Contact("David", "Brown", R.drawable.person3);
        Contact alex = new Contact("Alex", "Wilson", R.drawable.person4);
        Contact emma = new Contact("Emma", "Baker", R.drawable.person5);

        contacts = new Contact[5];
        contacts[0] = jon;
        contacts[1] = adam;
        contacts[2] = david;
        contacts[3] = alex;
        contacts[4] = emma;
        ContactsPageAdapter adapter = new ContactsPageAdapter(contacts);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
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

    public void btnEdit(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        EditContactDialogFragment editContactDialogFragment = new EditContactDialogFragment();
        editContactDialogFragment.setFragment("Edit Contact", contacts[viewPager.getCurrentItem()].firstName,
                contacts[viewPager.getCurrentItem()].lastName,this);
        editContactDialogFragment.show(fragmentManager, "edit contact");
    }

    @Override
    public void onDone(final String firstName, String lastName) {
        contacts[viewPager.getCurrentItem()].firstName = firstName;
        contacts[viewPager.getCurrentItem()].lastName = lastName;

        shownFirstName = (TextView)findViewById(R.id.firstName);
        shownLastName = (TextView)findViewById(R.id.lastName);
        shownFirstName.setText(firstName);
        shownLastName.setText(lastName);
    }
}
