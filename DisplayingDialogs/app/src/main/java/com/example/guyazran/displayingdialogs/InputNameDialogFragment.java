package com.example.guyazran.displayingdialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.util.zip.Inflater;

/**
 * Created by guyazran on 7/30/15.
 */
public class InputNameDialogFragment extends DialogFragment {
    EditText txtName;
    Button btnDone;
    String title;
    InputNameDialogListener listener;

    public void setFragment(String title, InputNameDialogListener listener){
        this.title = title;
        this.listener = listener;
    }

    public static interface InputNameDialogListener{
        void onFinnishInputDialog(String name);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inputname_dialog, container);
        txtName = (EditText)view.findViewById(R.id.txtName);
        btnDone = (Button)view.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                if (listener != null){
                    listener.onFinnishInputDialog(name);
                    dismiss();
                }
            }
        });
        txtName.requestFocus();

        //make the keyboard appear automatically
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        getDialog().setTitle(title);

        return view;
    }
}
