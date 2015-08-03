package com.example.guyazran.contacts;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by guyazran on 8/3/15.
 */
public class EditContactDialogFragmentTeacher extends DialogFragment {
    private EditText txtFirstName, txtLastName;
    private Button btnSave, btnCancel;
    private String title;
    private EditContactDialogListenerTeacher listener;
    private String firstName, lastName;

    public void setFragment(String title, EditContactDialogListenerTeacher listener,
                            String firstName, String lastName) {
        this.title = title;
        this.listener = listener;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_contact_teacher, container);
        txtFirstName = (EditText)view.findViewById(R.id.txtFirstNameTeacher);
        txtLastName = (EditText)view.findViewById(R.id.txtLastNameTeacher);
        txtFirstName.setText(firstName);
        txtLastName.setText(lastName);
        btnSave = (Button)view.findViewById(R.id.btnSaveTeacher);
        btnCancel = (Button)view.findViewById(R.id.btnCancelTeacher);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onEditComplete(txtFirstName.getText().toString(), txtLastName.getText().toString());
                }
                dismiss();
            }
        });

        txtFirstName.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        if (title != null)
            getDialog().setTitle(title);

        return view;
    }

    public static interface EditContactDialogListenerTeacher{
        void onEditComplete(String newFirstName, String newLastname);
    }
}
