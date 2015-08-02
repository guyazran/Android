package com.example.guyazran.displayingdialogs;

import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements InputNameDialogFragment.InputNameDialogListener, AreYouSureDialogFragment.AreYouSureDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void btnShowInputNameDialog(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        InputNameDialogFragment inputNameDialogFragment = new InputNameDialogFragment();
        inputNameDialogFragment.setFragment("Enter name", this);
        inputNameDialogFragment.setCancelable(false);
        inputNameDialogFragment.show(fragmentManager, "input dialog");
    }

    @Override
    public void onFinnishInputDialog(String name) {
        Toast.makeText(this, "name is: " + name, Toast.LENGTH_LONG).show();
    }

    public void btnShowAreYouSureDialog(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        AreYouSureDialogFragment areYouSureDialogFragment = new AreYouSureDialogFragment();
        areYouSureDialogFragment.setFragment("Yes or no", this);
        areYouSureDialogFragment.show(fragmentManager, "are you sure dialog");
    }

    //my methods
    @Override
    public void onSelectYes() {
        Toast.makeText(this, "Selected Yes", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onSelectNo() {
        Toast.makeText(this, "Selected No", Toast.LENGTH_LONG).show();
    }

    //teacher method
    @Override
    public void onFinishSelection(boolean answer) {
        Toast.makeText(this, "Selected: " + (answer ? "yes" : "no"), Toast.LENGTH_LONG).show();
    }
}
