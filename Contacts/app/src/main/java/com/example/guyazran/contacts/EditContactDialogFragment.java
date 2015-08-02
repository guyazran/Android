package com.example.guyazran.contacts;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by guyazran on 8/2/15.
 */
public class EditContactDialogFragment extends DialogFragment {

    private Button btnCancel, btnDone;
    private EditText txtFirstName, txtLastName;;
    private String title, currentFirstName, currentLastName;
    private EditContactDialogListener listener;

    public void setFragment(String title, String currentFirstName, String currentLastName ,EditContactDialogListener listener) {
        this.title = title;
        this.currentFirstName = currentFirstName;
        this.currentLastName = currentLastName;
        this.listener = listener;
    }

    public static interface EditContactDialogListener{
        void onDone(String firstName, String lastName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_contact, null);
        btnCancel = (Button)view.findViewById(R.id.btnCancel);
        btnDone = (Button)view.findViewById(R.id.btnDone);
        txtFirstName = (EditText)view.findViewById(R.id.txtEditFirstName);
        txtLastName = (EditText)view.findViewById(R.id.txtEditLastName);

        txtFirstName.setText(currentFirstName);
        txtLastName.setText(currentLastName);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    dismiss();
                }
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener !=null){
                    listener.onDone(txtFirstName.getText().toString(), txtLastName.getText().toString());
                    dismiss();
                }
            }
        });

        txtFirstName.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle(title);
        return view;
    }
}
